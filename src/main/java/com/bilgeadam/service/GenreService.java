package com.bilgeadam.service;

import com.bilgeadam.entity.Genre;
import com.bilgeadam.repository.GenreRepository;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService implements ICrudService<Genre,Long> {

    private final GenreRepository genreRepository;

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public Iterable<Genre> saveAll(Iterable<Genre> t) {
        return null;
    }

    @Override
    public Genre deleteById(Long aLong) {
        return null;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
