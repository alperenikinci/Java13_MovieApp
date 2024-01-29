package com.bilgeadam.controller;

import com.bilgeadam.entity.Movie;
import com.bilgeadam.entity.MovieComment;
import com.bilgeadam.service.MovieCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie-comment")
@RequiredArgsConstructor
public class MovieCommentController {

    private final MovieCommentService movieCommentService;

    @PostMapping("/save")
    public ResponseEntity<MovieComment> save(@RequestBody MovieComment movieComment){
        return ResponseEntity.ok(movieCommentService.save(movieComment));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Optional<MovieComment>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieCommentService.findById(id));
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<MovieComment>> findAll() {
        return ResponseEntity.ok(movieCommentService.findAll());
    }

    @GetMapping("find-by-movie-id")
    public ResponseEntity<List<MovieComment>> findByMovieId(Long id){
        return ResponseEntity.ok(movieCommentService.findByMovieId(id));
    }

    @GetMapping("find-by-movie-id-and-date-between/{id}")
    public ResponseEntity<List<MovieComment>> findByMovieIdAndDateBetween(@PathVariable Long id, String start, String end){
        return ResponseEntity.ok(movieCommentService.findByMovieIdAndDateBetween(id,start,end));
    }

    @GetMapping("find-content-length-greater-than")
    public ResponseEntity<List<MovieComment>> findContentLengthGreaterThan(Integer length) {
        return ResponseEntity.ok(movieCommentService.findContentLengthGreaterThan(length));
    }

}
