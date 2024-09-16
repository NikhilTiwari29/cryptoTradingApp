package com.nikhil.trading.controller;

import com.nikhil.trading.enums.VerificationType;
import com.nikhil.trading.modal.ForgotPasswordToken;
import com.nikhil.trading.modal.User;
import com.nikhil.trading.modal.VerificationCode;
import com.nikhil.trading.request.ResetPasswordRequest;
import com.nikhil.trading.request.UpdatePasswordRequest;
import com.nikhil.trading.response.ApiResponse;
import com.nikhil.trading.response.AuthResponse;
import com.nikhil.trading.service.EmailService;
import com.nikhil.trading.service.ForgetPasswordService;
import com.nikhil.trading.service.UserService;
import com.nikhil.trading.service.VerificationService;
import com.nikhil.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private VerificationService verificationService;

	@Autowired
	private ForgetPasswordService forgetPasswordService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserProfileHandler(
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwt(jwt);
		user.setPassword(null);

		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@GetMapping("/api/users/{userId}")
	public ResponseEntity<User> findUserById(
			@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserById(userId);
		user.setPassword(null);

		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@GetMapping("/api/users/email/{email}")
	public ResponseEntity<User> findUserByEmail(
			@PathVariable String email,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByEmail(email);

		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(
			@RequestHeader("Authorization") String jwt,
			@PathVariable String otp
	) throws Exception {

		User user = userService.findUserByJwt(jwt);
		VerificationCode verificationCode = verificationService.findUsersVerification(user.getId());
		String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)
				? verificationCode.getEmail() : verificationCode.getMobile();

		boolean isVerified = verificationService.verifyOtp(otp, verificationCode);

		if (isVerified) {
			User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
			verificationService.deleteVerificationCode(verificationCode);
			return ResponseEntity.ok(updatedUser);
		} else {
			throw new IllegalArgumentException("Invalid OTP");
		}
	}

	@PatchMapping("/auth/users/reset-password/verify-otp")
	public ResponseEntity<ApiResponse> resetPassword(
			@RequestParam String id,
			@RequestBody ResetPasswordRequest req) throws Exception {

		ForgotPasswordToken forgotPasswordToken = forgetPasswordService.findById(id);
		boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

		if (isVerified) {
			userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
			forgetPasswordService.deleteForgetPassword(forgotPasswordToken);
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setMessage("Password updated successfully");
			return ResponseEntity.ok(apiResponse);
		}
		throw new IllegalArgumentException("Invalid OTP");
	}

	@PostMapping("/auth/users/reset-password/send-otp")
	public ResponseEntity<AuthResponse> sendUpdatePasswordOTP(
			@RequestBody UpdatePasswordRequest req) throws Exception {

		User user = userService.findUserByEmail(req.getSendTo());
		String otp = OtpUtils.generateOtp();
		String id = UUID.randomUUID().toString();

		ForgotPasswordToken token = forgetPasswordService.findByUserId(user.getId());
		if (token == null) {
			token = forgetPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
		} else {
			token.setOtp(otp);
			forgetPasswordService.saveToken(token);
		}

		if (req.getVerificationType().equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
		}

		AuthResponse res = new AuthResponse();
		res.setSession(token.getId());
		res.setMessage("Password Reset OTP sent successfully.");

		return ResponseEntity.ok(res);
	}

	@PatchMapping("/api/users/verification/verify-otp/{otp}")
	public ResponseEntity<User> verifyOTP(
			@RequestHeader("Authorization") String jwt,
			@PathVariable String otp
	) throws Exception {

		User user = userService.findUserByJwt(jwt);
		VerificationCode verificationCode = verificationService.findUsersVerification(user.getId());

		boolean isVerified = verificationService.verifyOtp(otp, verificationCode);

		if (isVerified) {
			verificationService.deleteVerificationCode(verificationCode);
			User verifiedUser = userService.verifyUser(user);
			return ResponseEntity.ok(verifiedUser);
		}
		throw new IllegalArgumentException("Invalid OTP");
	}

	@PostMapping("/api/users/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOTP(
			@PathVariable VerificationType verificationType,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwt(jwt);

		VerificationCode verificationCode = verificationService.findUsersVerification(user.getId());

		if (verificationCode == null) {
			verificationCode = verificationService.sendVerificationOTP(user, verificationType);
		}

		if (verificationType.equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
		}

		return ResponseEntity.ok("Verification OTP sent successfully.");
	}
}
