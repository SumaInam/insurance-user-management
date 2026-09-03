package com.insurance.controller;

import com.insurance.entity.User;
import com.insurance.entity.UserLogin;
import com.insurance.repository.UserLoginRepository;
import com.insurance.repository.UserRepository;
import com.insurance.security.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login-page")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null) {
            model.addAttribute("emailError", "User Not Found");
            return "login";
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {

            model.addAttribute("passwordError", "Invalid Password");

            return "login";
        }
//        if(!passwordEncoder.matches(password, user.getPassword())) {
//
//            model.addAttribute("passwordError", "Invalid Password");
//            return "login";
//        }
        String token = jwtUtil.generateToken(user.getEmail());

        session.setAttribute("token", token);

        session.setAttribute("userName", user.getFirstName());


        UserLogin login = new UserLogin();

        login.setUser(user);
        login.setToken(token);
        login.setLoginTime(LocalDateTime.now());
        login.setTokenStartTime(LocalDateTime.now());
        login.setTokenEndTime(LocalDateTime.now().plusHours(3));

        login.setStatus("ACTIVE");

        userLoginRepository.save(login);

        session.setAttribute("token", token);

        session.setAttribute("userName", user.getFirstName());

        session.setAttribute("role", user.getUserType().getUserTypeName());

        session.setAttribute("user", user);

        System.out.println("LOGIN SUCCESS");

        System.out.println("TOKEN = " + token);

        System.out.println("USER = " + user.getFirstName());

        System.out.println("ROLE = " + user.getUserType().getUserTypeName());

        return "redirect:/dashboard";
    }
}
