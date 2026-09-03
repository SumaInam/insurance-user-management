package com.insurance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login-page",
                                "/register-page",
                                "/dashboard",
                                "/account-center",
                                "/profile",
                                "/profile/update",
                                "/address",
                                "/address/edit/**",
                                "/address/delete/**",
                                "/address/save",
                                "/change-password",
                                "/kyc",
                                "/kyc/upload",
                                "/policies",
                                "/policies/buy/**",
                                "/my-policies",
                                "/api/policies/download/**",
                                "/renew-policy/**",
                                "/payments",
                                "/payment/success",
                                "/api/payments/receipt/**",
                                "/claims",
                                "/claims/**",
                                "/claim-status/**",
                                "/claim-documents/**",
                                "/notifications",
                                "/notifications/read-all",
                                "/forgot-password",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/auth/login",
                                "/api/users/register"
                        ).permitAll()

                        .requestMatchers("/api/admin/**")
                        .hasRole("SUPER_AGENT")

                        .anyRequest()
                        .authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login-page")
                        .permitAll()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http)
//            throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers(
//                                "/",
//                                "/login-page",
//                                "/register-page",
//                                "/dashboard",
//                                "/profile",
//                                "/address",
//                                "/change-password",
//                                "/policies",
//                                "/claims",
//                                "/payments",
//                                "/notifications",
//                                "/forgot-password-page",
//                                "/css/**",
//                                "/js/**",
//                                "/images/**",
//                                "/api/auth/login",
//                                "/api/users/register"
//                        ).permitAll()
////                        .requestMatchers(
////                                "/",
////                                "/login-page",
////                                "/register-page",
////                                "/css/**",
////                                "/js/**",
////                                "/images/**",
////                                "/api/auth/login",
////                                "/api/users/register"
////                        ).permitAll()
//
//                        .requestMatchers("/api/admin/**")
//                        .hasRole("SUPER_AGENT")
//
//                        .anyRequest()
//                        .authenticated()
//                )
//              /*  .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers(
//                                "/api/auth/login",
//                                "/api/users/register"
//                        ).permitAll()
//
//                        .requestMatchers("/api/admin/**")
//                        .hasRole("SUPER_AGENT")
//
//                        .anyRequest()
//                        .authenticated()
//                )*/
//
//                .addFilterBefore(
//                        jwtFilter,
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }
}