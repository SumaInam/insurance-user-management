package com.insurance.service.impl;

import com.insurance.dto.LoginRequest;
import com.insurance.dto.LoginResponse;
import com.insurance.entity.User;
import com.insurance.entity.UserLogin;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.UserLoginRepository;
import com.insurance.repository.UserRepository;
import com.insurance.security.JwtUtil;
import com.insurance.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserLoginRepository loginRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        updateExpiredTokens();

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        LocalDateTime startTime = LocalDateTime.now();

        LocalDateTime endTime = startTime.plusHours(3);


        UserLogin login = new UserLogin();

        login.setUser(user);
        login.setToken(token);
        login.setLoginTime(startTime);
        login.setTokenStartTime(startTime);
        login.setTokenEndTime(endTime);
        login.setStatus("ACTIVE");

        loginRepository.save(login);

        return new LoginResponse(
                user.getUserId(),
                user.getUserType().getUserTypeName(),
                token,
                startTime,
                endTime
        );
    }

    @Override
    public void logout(String token) {

        String jwtToken = token.replace("Bearer ", "");

        UserLogin login = loginRepository
                .findByToken(jwtToken)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Token"));

        login.setStatus("LOGOUT");

        login.setTokenEndTime(LocalDateTime.now());

        loginRepository.save(login);
    }

    @Override
    public void updateExpiredTokens() {

        List<UserLogin> logins = loginRepository.findAll();

        for (UserLogin login : logins) {

            if ("ACTIVE".equals(login.getStatus()) &&
                    LocalDateTime.now().isAfter(login.getTokenEndTime())) {

                login.setStatus("EXPIRED");

                loginRepository.save(login);
            }
        }
    }
}
