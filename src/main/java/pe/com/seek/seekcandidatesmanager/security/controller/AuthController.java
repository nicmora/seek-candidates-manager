package pe.com.seek.seekcandidatesmanager.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.seek.seekcandidatesmanager.security.dto.CredentialsDTO;
import pe.com.seek.seekcandidatesmanager.security.util.JWTTokenGenerator;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${pe.com.seek.seek-candidates-manager.user}")
    private String user;
    @Value("${pe.com.seek.seek-candidates-manager.pass}")
    private String pass;

    private final JWTTokenGenerator jwtTokenGenerator;

    @PostMapping
    public ResponseEntity<Map<String, String>> getToken(@RequestBody CredentialsDTO credentials) {
        return Optional.ofNullable(credentials)
                .filter(creds -> user.equals(creds.username()) && pass.equals(creds.password()))
                .map(creds -> jwtTokenGenerator.getJWTToken(creds.username()))
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Username or password incorrect")));
    }

}
