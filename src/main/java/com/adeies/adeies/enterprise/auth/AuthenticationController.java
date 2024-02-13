package com.adeies.adeies.enterprise.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRq request,HttpServletResponse response) {
        AuthenticationResponse resp = service.authenticate(request);
        // Set Access-Token cookie
        Cookie accessToken = new Cookie("Access-Token", resp.getAccessToken());
        accessToken.setMaxAge((int) Duration.of(2, ChronoUnit.DAYS).getSeconds()); // Corrected
        accessToken.setSecure(true);
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        response.addCookie(accessToken);

        // Set Refresh-Token cookie
        Cookie refreshToken = new Cookie("Refresh-Token", resp.getRefreshToken());
        refreshToken.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).getSeconds()); // Corrected
        refreshToken.setSecure(true);
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");
        response.addCookie(refreshToken);

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        service.refreshToken(request, response);
    }

}
