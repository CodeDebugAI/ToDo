package com.example.toDo.controller;


import com.example.toDo.model.User;
import com.example.toDo.repository.UserRepository;
import com.example.toDo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth") //base path for all auth endpoints
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //constructor injection for JwtUtil
    public AuthController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> userMap){
        //Hardcoded credentials for now
        String username = userMap.get("username");
        String password = userMap.get("password");

        return userRepository.findByUsername(username)
                .map(user -> {
                    //check password
                    if(passwordEncoder.matches(password, user.getPassword())){
                        String token = jwtUtil.generateToken(username);
                        return ResponseEntity.ok(Map.of("token",token));
                    }else{
                        return ResponseEntity.status(401)
                                .body(Map.of("error","Invalid username or password"));
                    }
                })
                .orElseGet(()-> ResponseEntity.status(401)
                        .body(Map.of("error","Invalid username or password")));

        //Check if credentials are correct
//        if("user".equals(username) && "user".equals(password)){
//            //Generate JWT
//            String token = jwtUtil.generateToken(username);
//
//            //Return token in response
//            return ResponseEntity.ok(Map.of("token", token));
//        }
//        // if credentials wrong ->return 401 Unauthorized
//        return ResponseEntity.status(401)
//                .body(Map.of("error","Invalid username or password"));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody Map<String,String> userMap){
        String username = userMap.get("username");
        String password = userMap.get("password");

        // Check if user already exists
        if(userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "User already exists"));
        }

        // Save new user with hashed password
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
