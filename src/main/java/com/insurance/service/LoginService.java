package com.insurance.service;

import com.insurance.dto.LoginRequest;
import com.insurance.dto.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);

    void logout(String token);

    void updateExpiredTokens();
}