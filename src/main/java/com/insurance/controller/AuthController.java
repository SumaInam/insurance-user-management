package com.insurance.controller;

import com.insurance.dto.LoginRequest;
import com.insurance.dto.LoginResponse;
import com.insurance.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        return loginService.login(request);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token){

        loginService.logout(token);

        return "Logout Successful";
    }
}