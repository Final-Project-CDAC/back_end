package com.tinyguard.controller;

import com.tinyguard.dto.UserDTO;
import com.tinyguard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }
    
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }
    
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/parent/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    
    @PutMapping("/parent/profile")
    public ResponseEntity<UserDTO> updateProfile(Authentication authentication, @Valid @RequestBody UserDTO userDTO) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.updateProfile(email, userDTO));
    }
    
    @PutMapping("/parent/profile/password")
    public ResponseEntity<Map<String, String>> changePassword(Authentication authentication, @RequestBody Map<String, String> request) {
        try {
            String email = authentication.getName();
            String currentPassword = request.get("currentPassword");
            String newPassword = request.get("newPassword");
            userService.changePassword(email, currentPassword, newPassword);
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
