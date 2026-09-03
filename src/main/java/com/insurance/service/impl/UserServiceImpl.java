package com.insurance.service.impl;

import com.insurance.dto.ChangePasswordRequest;
import com.insurance.dto.UserRequest;
import com.insurance.dto.UserResponse;
import com.insurance.entity.User;
import com.insurance.entity.UserType;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.exception.UserAlreadyExistsException;
import com.insurance.repository.UserRepository;
import com.insurance.repository.UserTypeRepository;
import com.insurance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTypeRepository userTypeRepository;

    @Override
    public UserResponse registerUser(UserRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Email already registered");
        }

        if(request.getUserTypeId() == null){
            throw new RuntimeException(
                    "User Type is mandatory");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setStatus(request.getStatus());
        UserType userType = userTypeRepository
                .findById(request.getUserTypeId())
                .orElseThrow(() ->
                        new RuntimeException("Invalid User Type"));
        user.setUserType(userType);
        //user.setCreateDate(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        savedUser.setUserId(savedUser.getId());
        savedUser = userRepository.save(savedUser);
        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setUserId(savedUser.getUserId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setPhoneNumber(savedUser.getPhoneNumber());
        response.setDateOfBirth(savedUser.getDateOfBirth());
        response.setGender(savedUser.getGender());
        response.setCreateDate(savedUser.getCreateDate());
        response.setUpdateDate(savedUser.getUpdateDate());
        response.setStatus(savedUser.getStatus());
        response.setUserTypeName(savedUser.getUserType().getUserTypeName());

        return response;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }


    @Override
    public User updateUser(Long id, User user) {

        User existingUser = getUserById(id);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setGender(user.getGender());
        existingUser.setStatus(user.getStatus());

        if (user.getUserType() != null) {

            UserType userType =
                    userTypeRepository
                            .findById(
                                    user.getUserType()
                                            .getUserTypeId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Invalid User Type"));

            existingUser.setUserType(userType);
        }

        existingUser.setUpdateDate(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = getUserById(id);

        user.setStatus("INACTIVE");

        userRepository.delete(user);
    }

    //Change Password Method
    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {

            throw new RuntimeException("Old Password is incorrect");
        }

        if(!request.getNewPassword().equals(request.getConfirmPassword())) {

            throw new RuntimeException("New Password and Confirm Password do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

}