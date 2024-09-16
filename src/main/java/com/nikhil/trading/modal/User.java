package com.nikhil.trading.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nikhil.trading.enums.USER_ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = false, length = 100) // Full name is mandatory and has a max length
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true) // Email is mandatory and must be unique
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Hide password in responses
    @Column(nullable = false) // Password is mandatory
    private String password;

    private String mobile;

    @Enumerated(EnumType.STRING) // Store enum as a string in the database
    @Column(nullable = false) // User role is mandatory
    @Builder.Default
    private USER_ROLE userRole = USER_ROLE.ROLE_CUSTOMER;

    @Embedded
    @Builder.Default
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    @JsonIgnore // Do not expose in API responses
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime updatedAt;

    private boolean isVerified = false;
}
