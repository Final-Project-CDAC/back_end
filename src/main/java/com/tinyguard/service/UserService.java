package com.tinyguard.service;

import com.tinyguard.dto.UserDTO;
import com.tinyguard.entity.User;
import com.tinyguard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ActivityLogService activityLogService;
    
    public List<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }
    
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }
    
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFullName(userDTO.getFullName());
        user.setActive(userDTO.getActive());
        
        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDTO.class);
    }
    
    public UserDTO updateProfile(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (userDTO.getFullName() == null || userDTO.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Full name is required");
        }
        user.setFullName(userDTO.getFullName());
        
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(email)) {
            if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new RuntimeException("Invalid email format");
            }
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(userDTO.getEmail());
        }
        
        try {
            User updated = userRepository.save(user);
            return modelMapper.map(updated, UserDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update profile");
        }
    }
    
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        activityLogService.logActivity("PASSWORD_CHANGED", "Password changed for: " + user.getFullName(), email);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
