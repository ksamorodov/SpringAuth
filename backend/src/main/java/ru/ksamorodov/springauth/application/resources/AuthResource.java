package ru.ksamorodov.springauth.application.resources;

import liquibase.pro.packaged.H;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ksamorodov.springauth.application.dao.Role;
import ru.ksamorodov.springauth.application.dao.UserPrincipal;
import ru.ksamorodov.springauth.application.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final UserRepository userRepository;

    @GetMapping("/logged-in")
    public ResponseEntity<?> loggedIn(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(principal);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal != null && principal.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.ok(userRepository.getAllUsers());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUserById(@AuthenticationPrincipal UserPrincipal principal, @RequestParam UUID id) {
        if (principal != null) {
            return ResponseEntity.ok(userRepository.deleteUser(id));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> deleteUserById(@RequestBody UserPrincipal user) {
        Optional<UserPrincipal> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
            return ResponseEntity.ok(userRepository.register(user));
        } else {
            return ResponseEntity.badRequest().body("This username already exists");
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUserByAdmin(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UserPrincipal user) {
        if (principal == null || !principal.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<UserPrincipal> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
            return ResponseEntity.ok(userRepository.register(user));
        } else {
            return ResponseEntity.badRequest().body("This username already exists");
        }
    }

    @PutMapping("/block-user")
    public ResponseEntity<?> blockUser(@AuthenticationPrincipal UserPrincipal principal, @RequestParam UUID id) {
        if (principal.getRole().equals(Role.ADMIN)) {
            userRepository.blockUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/unblock-user")
    public ResponseEntity<?> unblockUser(@AuthenticationPrincipal UserPrincipal principal, @RequestParam UUID id) {
        if (principal.getRole().equals(Role.ADMIN)) {
            userRepository.unblockUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserPrincipal principal, @RequestParam Optional<UUID> id, @RequestBody String password) {
        if (principal != null) {
            if (principal.getRole().equals(Role.ADMIN)) {
                userRepository.setPassword(id.get(), password);
            } else {
                userRepository.setPassword(principal.getId(), password);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
