package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.AuthResponse;
import com.polylvst.simplepos.domain.dtos.LoginRequest;
import com.polylvst.simplepos.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        String tokenValue = authenticationService.generateToken(userDetails);
        long expiresInMiliseconds = 86400;
        AuthResponse authResponse = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(expiresInMiliseconds)
                .build();
        return ResponseEntity.ok(authResponse);
    }

}
