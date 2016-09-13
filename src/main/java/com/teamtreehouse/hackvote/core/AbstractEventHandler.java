package com.teamtreehouse.hackvote.core;

import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractEventHandler {
    @Autowired
    private UserService userService;

    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.loadUserByUsername(username);
    }
}
