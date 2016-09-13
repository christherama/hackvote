package com.teamtreehouse.hackvote.security;

import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private final String authHeader;
    private final UserRepository users;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthenticationFilter(
            UserRepository users,
            JwtUtils jwtUtils,
            @Value("${jwt.header}") String authHeader) {
        this.users = users;
        this.authHeader = authHeader;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get auth token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(authHeader);

        // Get username from token
        String username = jwtUtils.getUsername(token);

        System.out.printf("%n%nTOKEN: %s%nCURRENT USER: %s%n%n",token,username);

        // Authenticate if token is valid
        // If token isn't valid, username will be null and won't be authenticated
        // => Resources will be secured in such a way that will prevent operations
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = users.findByUsername(username);
            if(jwtUtils.isValid(token,user)) {
                System.out.printf("%n%nAUTHENTICATED!%n%n");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request,response);
    }
}
