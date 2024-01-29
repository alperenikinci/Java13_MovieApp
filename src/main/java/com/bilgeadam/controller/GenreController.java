package com.bilgeadam.controller;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.Genre;
import com.bilgeadam.entity.MovieComment;
import com.bilgeadam.entity.User;
import com.bilgeadam.service.GenreService;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;


    @PostMapping("/save")
    public ResponseEntity<Genre> save(@RequestBody Genre genre){
        return ResponseEntity.ok(genreService.save(genre));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Optional<Genre>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id));
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<Genre>> findAll() {
        return ResponseEntity.ok(genreService.findAll());
    }
}
