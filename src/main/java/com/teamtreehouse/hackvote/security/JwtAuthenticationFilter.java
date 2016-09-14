package com.teamtreehouse.hackvote.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthenticationFilter(
            JwtUtils jwtUtils,
            @Value("${jwt.header}") String authHeader) {
        this.authHeader = authHeader;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get auth token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(authHeader);

        // Authenticate if token is valid
        // If token isn't valid, username will be null and won't be authenticated
        // => Resources will be secured in such a way that will prevent operations
        if(jwtUtils.isValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUtils.getUsername(token),null, AuthorityUtils.createAuthorityList(jwtUtils.getRole(token)));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);
    }
}
