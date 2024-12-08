package com.jonichi.envelope.auth.infrastucture.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
