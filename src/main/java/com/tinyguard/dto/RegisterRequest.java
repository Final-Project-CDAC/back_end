package com.tinyguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    @NotBlank
    private String role;
}
