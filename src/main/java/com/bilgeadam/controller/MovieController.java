package com.bilgeadam.controller;

import com.bilgeadam.entity.Movie;
import com.bilgeadam.entity.User;
import com.bilgeadam.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/save")
    public ResponseEntity<Movie> save(@RequestBody Movie movie){
        return ResponseEntity.ok(movieService.save(movie));
    }

    //-> @RequestParam ->> property isimleri url de gözükür.
    //-> @PathVariable ->> property isimleri url'de gözükmez, yalnızca atılan sorgudaki parametre degeri gözükür.
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Optional<Movie>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<Movie>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/find-all-by-rating-greater-than")
    public ResponseEntity<List<Movie>> findAllByRatingGreaterThan(Double rating){
        return ResponseEntity.ok(movieService.findAllByRatingGreaterThan(rating));
    }

    @GetMapping("/find-by-premiered-before")
    public ResponseEntity<List<Movie>> findByPremieredBefore(String date){
        return ResponseEntity.ok(movieService.findByPremieredBefore(date));
    }

    @GetMapping("/find-by-rating-in")
    public ResponseEntity<List<Movie>> findByRatingIn(){
        return ResponseEntity.ok(movieService.findByRatingIn(List.of(7D,8D,9D)));
    }
}
