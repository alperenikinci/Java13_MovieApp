package com.bilgeadam.service;

import com.bilgeadam.entity.Genre;
import com.bilgeadam.entity.MovieComment;
import com.bilgeadam.repository.GenreRepository;
import com.bilgeadam.repository.MovieCommentRepository;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieCommentService implements ICrudService<MovieComment, Long> {

    private final MovieCommentRepository movieCommentRepository;
    private final MovieService movieService;


    @Override
    public MovieComment save(MovieComment movieComment) {
        return movieCommentRepository.save(movieComment);
    }

    @Override
    public MovieComment update(MovieComment movieComment) {
        return null;
    }

    @Override
    public Iterable<MovieComment> saveAll(Iterable<MovieComment> t) {
        return null;
    }

    @Override
    public MovieComment deleteById(Long id) {
        return null;
    }

    @Override
    public Optional<MovieComment> findById(Long id) {
        return movieCommentRepository.findById(id);
    }

    @Override
    public List<MovieComment> findAll() {
        return movieCommentRepository.findAll();
    }

    public List<MovieComment> findByMovieId(Long id) {
        return movieCommentRepository.findByMovieId(id);
    }

    public List<MovieComment> findByMovieIdAndDateBetween(Long id, String start, String end) {
        if (movieService.findById(id).isPresent()) {
            LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return movieCommentRepository.findByMovieIdAndDateBetween(id, startDate, endDate);
        } else {
            throw new NullPointerException("Film bulunamadı...");
        }
    }

    public List<MovieComment> findContentLengthGreaterThan(Integer length) {
        return movieCommentRepository.findContentLengthGreaterThan(length);
    }
}
