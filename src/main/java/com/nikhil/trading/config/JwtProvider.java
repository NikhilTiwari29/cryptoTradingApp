package com.nikhil.trading.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {

    // Secret key for signing the JWT
    static final String SECRET_KEY = "mjauwoelspwuwnnsywj83janshw73jsnao3ueyeddkskgansjdndgdekeje83j3neudsuwjn";
    static SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Expiration time for the token (24 hours in milliseconds)
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    // Generate a JWT token with claims like email and authorities
    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String role = populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set token expiration
                .claim("email", auth.getName()) // Set email as a claim
                .claim("authorities", role) // Set authorities (roles) as a claim
                .signWith(key) // Sign the JWT with the secret key
                .compact();
    }

    // Convert the authorities (roles) to a comma-separated string
    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            auth.add(grantedAuthority.getAuthority());
        }
        return String.join(",", auth);
    }

    // Extract the email from the JWT token
    public static String getEmailFromJwtToken(String jwtToken) {
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7); // Remove "Bearer " prefix
        } else {
            throw new IllegalArgumentException("JWT token does not begin with Bearer");
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return (String) claims.get("email"); // Extract email from claims
    }
}

