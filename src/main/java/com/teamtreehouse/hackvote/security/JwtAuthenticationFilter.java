package com.teamtreehouse.hackvote.security;

import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {
    private String authHeader;
    private UserRepository users;

    @Autowired
    public JwtAuthenticationFilter(
            UserRepository users,
            @Value("${jwt.header}") String authHeader) {
        this.users = users;
        this.authHeader = authHeader;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get auth token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Jwt token = new Jwt(httpRequest.getHeader(authHeader));

        // Get username from token
        String username = token.getUsername();

        // Authenticate if token is valid
        // If token isn't valid, username will be null and won't be authenticated
        // => Resources will be secured in such a way that will prevent operations
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = users.findByUsername(username);
            if(token.isValid(user)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request,response);
    }
}
