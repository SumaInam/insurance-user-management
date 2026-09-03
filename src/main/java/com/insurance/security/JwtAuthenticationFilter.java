package com.insurance.security;

import com.insurance.entity.User;
import com.insurance.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {

        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        //if (path.equals("/api/auth/login") || path.equals("/api/auth/register")){
            //filterChain.doFilter(request, response);
            //return; }

        if (path.equals("/")
                || path.equals("/login-page")
                || path.equals("/register-page")
                || path.equals("/forgot-password-page")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.equals("/api/auth/login")
                || path.equals("/api/users/register")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");


        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {


                String email = jwtUtil.extractUsername(token);

                User user = userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

                String role = user.getUserType().getUserTypeName();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + role
                                        )));
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

            } else {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.getWriter().write("Token Expired. Please Login Again");

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}