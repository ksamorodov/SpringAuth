package ru.ksamorodov.springauth.application.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ksamorodov.springauth.application.dao.UserPrincipal;
import ru.ksamorodov.springauth.application.repository.UserRepository;

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
        if (principal != null) {
            return ResponseEntity.ok(userRepository.getAllUsers());
        }
        return ResponseEntity.badRequest().build();
    }
}
