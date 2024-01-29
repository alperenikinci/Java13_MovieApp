package com.bilgeadam.service;

import com.bilgeadam.entity.Genre;
import com.bilgeadam.repository.GenreRepository;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return genreRepository.saveAll(t);
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

    public List<Long> createGenresWithNames(List<String> genres) {
        List<Long> movieGenreList = new ArrayList<>();
        for(String name: genres){
            Optional<Genre> genre = genreRepository.findOptionalByName(name);
            if(genre.isPresent()){
                movieGenreList.add(genre.get().getId());
            }else {
                Genre myGenre= Genre.builder().name(name).build();
                genreRepository.save(myGenre);
                movieGenreList.add(myGenre.getId());
            }
        }
        return movieGenreList;
    }
}
