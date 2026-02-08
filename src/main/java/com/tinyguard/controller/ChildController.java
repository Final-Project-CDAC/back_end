package com.tinyguard.controller;

import com.tinyguard.dto.ChildDTO;
import com.tinyguard.service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/parent/children")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class ChildController {
    
    private final ChildService childService;
    
    @PostMapping
    public ResponseEntity<ChildDTO> createChild(@Valid @RequestBody ChildDTO childDTO, Authentication auth) {
        return ResponseEntity.ok(childService.createChild(childDTO, auth.getName()));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ChildDTO> updateChild(@PathVariable Long id, @Valid @RequestBody ChildDTO childDTO) {
        try {
            return ResponseEntity.ok(childService.updateChild(id, childDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<ChildDTO>> getChildren(Authentication auth) {
        return ResponseEntity.ok(childService.getChildrenByParent(auth.getName()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ChildDTO> getChild(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(childService.getChildById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        try {
            childService.deleteChild(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
