package com.insurance.repository;

import com.insurance.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    Optional<UserLogin> findByToken(String token);
    List<UserLogin> findByStatus(String status);

}