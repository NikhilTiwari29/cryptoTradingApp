package com.nikhil.trading.controller;

import com.nikhil.trading.config.JwtProvider;
import com.nikhil.trading.modal.TwoFactorOtp;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.repository.UserRepository;
import com.nikhil.trading.request.LoginRequest;
import com.nikhil.trading.response.AuthResponse;
import com.nikhil.trading.service.*;
import com.nikhil.trading.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetails;

	@Autowired
	private UserService userService;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private TwoFactorOtpService twoFactorOtpService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new Exception("Email is already in use");
		}

		// Create new user
		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setMobile(user.getMobile());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(createdUser);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setMessage("Register Success");
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) throws Exception, MessagingException {
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		User user = userService.findUserByEmail(loginRequest.getEmail());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = JwtProvider.generateToken(authentication);

		// Check if two-factor authentication is enabled
		if (user.getTwoFactorAuth() != null && user.getTwoFactorAuth().isEnabled()) {
			String otp = OtpUtils.generateOtp();
			TwoFactorOtp existingOtp = twoFactorOtpService.findByUser(user.getId());

			// Delete old OTP if it exists
			if (existingOtp != null) {
				twoFactorOtpService.deleteTwoFactorOtp(existingOtp);
			}

			// Create and save the new OTP
			TwoFactorOtp newOtp = twoFactorOtpService.createTwofactorOtp(user, otp, token);

			// Send OTP via email
			emailService.sendVerificationOtpEmail(user.getEmail(), otp);

			AuthResponse authResponse = new AuthResponse();
			authResponse.setMessage("Two-factor authentication enabled, OTP sent");
			authResponse.setIsTwoFactorAuthEnable(true);
			authResponse.setSession(newOtp.getId());

			return new ResponseEntity<>(authResponse, HttpStatus.OK);
		}

		// If 2FA is not enabled, return JWT directly
		AuthResponse authResponse = new AuthResponse();
		authResponse.setMessage("Login Success");
		authResponse.setJwtToken(token);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp, @RequestParam String id) throws Exception {
		TwoFactorOtp twoFactorOtp = twoFactorOtpService.findById(id);

		if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp, otp)) {
			AuthResponse authResponse = new AuthResponse();
			authResponse.setMessage("Two-factor authentication verified");
			authResponse.setIsTwoFactorAuthEnable(true);
			authResponse.setJwtToken(twoFactorOtp.getJwtToken());

			// delete the OTP after successful verification
			twoFactorOtpService.deleteTwoFactorOtp(twoFactorOtp);

			return new ResponseEntity<>(authResponse, HttpStatus.OK);
		}

		throw new Exception("Invalid OTP");
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetails.loadUserByUsername(username);
		if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
