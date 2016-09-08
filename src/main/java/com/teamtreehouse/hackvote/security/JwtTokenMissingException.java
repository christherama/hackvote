package com.teamtreehouse.hackvote.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class JwtTokenMissingException extends AuthenticationCredentialsNotFoundException {
    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
