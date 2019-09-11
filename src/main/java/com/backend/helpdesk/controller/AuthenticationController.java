package com.backend.helpdesk.controller;


import com.backend.helpdesk.configurations.TokenProvider;
import com.backend.helpdesk.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<?> login(@RequestHeader("token-google") String tokenGoogle) throws IOException {
        String email = authenticationService.getEmailFromTokenUser(tokenGoogle);
        System.out.println(email);
        return authenticationService.generateToken(email);
    }
}
