package com.bilgeadam.service;

import com.bilgeadam.entity.Movie;
import com.bilgeadam.entity.MovieComment;
import com.bilgeadam.repository.MovieRepository;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService implements ICrudService<Movie,Long> {

    private final MovieRepository movieRepository;
    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Movie movie) {
        return null;
    }

    @Override
    public Iterable<Movie> saveAll(Iterable<Movie> t) {
        return movieRepository.saveAll(t);
    }

    @Override
    public Movie deleteById(Long aLong) {
        return null;
    }

    @Override
    public Optional<Movie> findById(Long aLong) {
        Optional<Movie> optionalMovie = movieRepository.findById(aLong);
        if(optionalMovie.isEmpty()){
            throw new NullPointerException("Böyle bir film bulunamadı");
        }
        return optionalMovie;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movieList = movieRepository.findAll();
        if(movieList.isEmpty()){
            throw new NullPointerException("Liste boş");
        }
        return movieList;
    }

    public List<Movie> findAllByRatingGreaterThan(Double rating){
        return movieRepository.findAllByRatingGreaterThan(rating);
    }

    public List<Movie> findByPremieredBefore(String premiered){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDate = LocalDate.parse(premiered, formatter);
        return movieRepository.findByPremieredBefore(parsedDate);
    }

    public List<Movie> findByRatingIn(List<Double> ratings){
        return movieRepository.findByRatingIn(List.of(7D,8D,9D));
    }







}
