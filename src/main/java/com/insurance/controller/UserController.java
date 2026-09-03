package com.insurance.controller;

import com.insurance.dto.ChangePasswordRequest;
import com.insurance.dto.UserRequest;
import com.insurance.dto.UserResponse;
import com.insurance.entity.User;
import com.insurance.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody UserRequest request){

        return userService.registerUser(request);
    }


    @GetMapping
    public List<User> getUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){

        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user){

        return userService.updateUser(id,user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){

        userService.deleteUser(id);

        return "User Deleted Successfully";
    }

    //Change Password
    @PostMapping("/{id}/change-password")
    public String changePassword(@PathVariable Long id,
            @RequestBody ChangePasswordRequest request){

        userService.changePassword(id, request);

        return "Password Changed Successfully";
    }
}