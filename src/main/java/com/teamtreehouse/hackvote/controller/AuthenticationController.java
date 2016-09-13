package com.teamtreehouse.hackvote.controller;

import com.teamtreehouse.hackvote.security.JwtUtils;
import com.teamtreehouse.hackvote.security.JwtAuthenticationResponse;
import com.teamtreehouse.hackvote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authManager,
            UserService userService,
            JwtUtils jwtUtils) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @RequestMapping(value = "${jwt.route.auth}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, @RequestParam String password) throws AuthenticationException {
        // Perform the security
        final Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails user = userService.loadUserByUsername(username);
        final String token = jwtUtils.generateFromUserDetails(user);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
