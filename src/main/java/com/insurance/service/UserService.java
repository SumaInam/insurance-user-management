package com.insurance.service;

import com.insurance.dto.ChangePasswordRequest;
import com.insurance.dto.UserRequest;
import com.insurance.dto.UserResponse;
import com.insurance.entity.User;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest request);
    //User registerUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    void changePassword(Long userId, ChangePasswordRequest request);
}