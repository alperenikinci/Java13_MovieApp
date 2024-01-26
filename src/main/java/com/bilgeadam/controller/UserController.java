package com.bilgeadam.controller;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/find-by-id")
    public ResponseEntity<Optional<User>> findById(Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/find-all") //ADMIN tarafından erişilebilir yapılmalı.
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(Long id){
        return ResponseEntity.ok(userService.deleteById(id));
    }

    //basic register
    @PostMapping("/register")
    public ResponseEntity<User> register(String name, String surname, String email,String password,String rePassword){
        return ResponseEntity.ok(userService.register(name, surname, email, password, rePassword));
    }

    //basic login
    @PostMapping("/login")
    public ResponseEntity<User> login (String email, String password){
        return ResponseEntity.ok(userService.login(email,password));
    }

    //dto register
    @PostMapping("/register-dto")
    public ResponseEntity<RegisterResponseDto> registerDto(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerDto(dto));
    }

    //dto login

    @PostMapping("/login-dto")
    public ResponseEntity<LoginResponseDto> loginDto(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(userService.loginDto(dto));
    }

    //mapper register
    @PostMapping("/register-mapper")
    public ResponseEntity<RegisterResponseDto> registerMapper(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerMapper(dto));
    }

    //mapper login
    @PostMapping("/login-mapper")
    public ResponseEntity<LoginResponseDto> loginMapper(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(userService.loginMapper(dto));
    }








}