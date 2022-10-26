package ru.springauth.application.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.springauth.application.dao.Role;
import ru.springauth.application.dao.UserPrincipal;
import ru.springauth.application.repository.UserRepository;
import ru.springauth.application.service.FileUtilService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final UserRepository userRepository;

    @GetMapping("/logged-in")
    public ResponseEntity<?> loggedIn(@AuthenticationPrincipal UserPrincipal principal) {
        userRepository.insertAllUsers(FileUtilService.read());
        return ResponseEntity.ok(principal);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal UserPrincipal principal) {
        userRepository.insertAllUsers(FileUtilService.read());
        if (principal != null && principal.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.ok(userRepository.getAllUsers());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUserById(@AuthenticationPrincipal UserPrincipal principal, @RequestParam UUID id) {
        userRepository.insertAllUsers(FileUtilService.read());
        if (principal != null) {
            return ResponseEntity.ok(userRepository.deleteUser(id));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> deleteUserById(@RequestParam String username) {
        userRepository.insertAllUsers(FileUtilService.read());
        Optional<UserPrincipal> byUsername = userRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            return ResponseEntity.ok(userRepository.register(username));
        } else {
            return ResponseEntity.badRequest().body("This username already exists");
        }
    }

    @GetMapping("/get-connection")
    public ResponseEntity<?> getConnection() {
        List<UserPrincipal> read = FileUtilService.read();
        userRepository.insertAllUsers(read);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block-user")
    public ResponseEntity<?> blockUser(@AuthenticationPrincipal UserPrincipal principal, @RequestParam UUID id) {
        userRepository.insertAllUsers(FileUtilService.read());
        if (principal.getRole().equals(Role.ADMIN)) {
            userRepository.blockUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/unblock-user")
    public ResponseEntity<?> unblockUser(@AuthenticationPrincipal UserPrincipal principal, @RequestParam UUID id) {
        userRepository.insertAllUsers(FileUtilService.read());
        if (principal.getRole().equals(Role.ADMIN)) {
            userRepository.unblockUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserPrincipal principal, @RequestParam Optional<UUID> id, @RequestBody String password) {
        userRepository.insertAllUsers(FileUtilService.read());
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

    @GetMapping("/set-validation-password")
    public ResponseEntity<?> setValidationPassword(@AuthenticationPrincipal UserPrincipal principal, @RequestParam Optional<UUID> id, @RequestParam Boolean value) {
        userRepository.insertAllUsers(FileUtilService.read());
        if (principal != null && principal.getRole().equals(Role.ADMIN)) {
            if (id.isPresent()) {
                userRepository.setValidationPassword(id.get(), value);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/check-old-password")
    public ResponseEntity<?> checkOldPassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Optional<String> password) {
        userRepository.insertAllUsers(FileUtilService.read());
        if (principal != null) {
            if (userRepository.checkOldPassword(principal.getUsername(), password.orElse(""))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
