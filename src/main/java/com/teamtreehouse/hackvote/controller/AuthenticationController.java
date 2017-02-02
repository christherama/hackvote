package com.teamtreehouse.hackvote.controller;

import com.teamtreehouse.hackvote.security.AuthRequest;
import com.teamtreehouse.hackvote.security.JwtAuthenticationResponse;
import com.teamtreehouse.hackvote.security.JwtUtils;
import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

  @RequestMapping(value = "${jwt.route.auth}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public JwtAuthenticationResponse createAuthenticationToken(@RequestBody AuthRequest auth) throws AuthenticationException {
    // Perform authentication
    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Generate token
    User user = userService.loadUserByUsername(auth.getUsername());
    String token = jwtUtils.generateFromUser(user);

    // Return the token
    return new JwtAuthenticationResponse(token);
  }
}
