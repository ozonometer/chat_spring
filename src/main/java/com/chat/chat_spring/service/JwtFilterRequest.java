package com.chat.chat_spring.service;

import com.chat.chat_spring.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Requirement 2, handle and process HTTP requests.
 * JWT token filter, validates JWT token
 */
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    public JwtFilterRequest(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // get Authorization header
        if (authHeader != null && authHeader.startsWith("Token ")) {
            jwtToken = authHeader.substring(6); // length of startWith parameter string
            username = jwtUtils.extractUsername(jwtToken);
        }
        // if username present and if there is no current Spring Security Contex, build new Spring Security Contex from user present in jwt token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails currentUserDetails = userService.loadUserByUsername(username);
            boolean tokenValidated = jwtUtils.validateToken(jwtToken, currentUserDetails);
            if (tokenValidated) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(currentUserDetails,
                        null, currentUserDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }
        filterChain.doFilter(request, response);
    }
}
