package com.teamtreehouse.hackvote.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository users;

    @Autowired
    public UserService(UserRepository users) {
        this.users = users;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
