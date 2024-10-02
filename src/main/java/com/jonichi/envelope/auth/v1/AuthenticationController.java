package com.jonichi.envelope.auth.v1;

import com.jonichi.envelope.common.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Resource<AuthenticationResult>> register(
            @RequestBody @Valid RegisterRequest request
    ) {

        final AuthenticationResult result = service.register(request);
        final Resource<AuthenticationResult> resource = Resource.Success
                .<AuthenticationResult>builder()
                .message("Success")
                .data(result)
                .build();

        return ResponseEntity.ok(resource);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Resource<AuthenticationResult>> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {

        final AuthenticationResult result = service.authenticate(request);
        final Resource<AuthenticationResult> resource = Resource.Success
                .<AuthenticationResult>builder()
                .message("Success")
                .data(result)
                .build();

        return ResponseEntity.ok(resource);
    }

}
