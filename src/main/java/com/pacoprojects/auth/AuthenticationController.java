package com.pacoprojects.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService serviceAuthentication;

//    @PostMapping(path = "register")

    @PostMapping(path = "authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest authRequest, HttpServletResponse response) {
        return ResponseEntity.ok(serviceAuthentication.authenticate(authRequest, response));
    }
}
