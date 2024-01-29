package com.bilgeadam.utility;

import com.bilgeadam.entity.Movie;
import com.bilgeadam.entity.MovieComment;
import com.bilgeadam.entity.User;
import com.bilgeadam.service.GenreService;
import com.bilgeadam.service.MovieCommentService;
import com.bilgeadam.service.MovieService;
import com.bilgeadam.service.UserService;
import com.bilgeadam.utility.data.Sample;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataImpl implements ApplicationRunner {

    private final MovieService movieService;
    private final GenreService genreService;
    private final MovieCommentService movieCommentService;
    private final UserService userService;
    @Override
    public void run(ApplicationArguments args) throws Exception {

//        getAllMovies();
//        createUser();
//        setMovieCommentsByMovieId();

    }
    private void getAllMovies() {
        try {
            //https://api.tvmaze.com/shows
            URL url = new URL("https://api.tvmaze.com/shows");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String value = "";
            value = bufferedReader.readLine();
            Sample[] array = new Gson().fromJson(value,Sample[].class);

            Arrays.asList(array).forEach(x -> {
                Movie movie = null;
                if(x.network==null){
                    movie = Movie.builder()
                            .id(x.getId())
                            .url(x.getUrl())
                            .image(x.getImage().medium)
                            .language(x.getLanguage())
                    .premiered(LocalDate.parse(x.getPremiered()))
                    .summary(x.getSummary())
                    .name(x.getName())
                    .genres(genreService.createGenresWithNames(x.genres))
                    .rating(x.getRating().getAverage())
                    .build();
        } else {
            movie = Movie.builder()
                    .id(x.getId())
                    .url(x.getUrl())
                    .image(x.getImage().medium)
                    .language(x.getLanguage())
                    .premiered(LocalDate.parse(x.getPremiered()))
                    .summary(x.getSummary())
                    .name(x.getName())
                    .genres(genreService.createGenresWithNames(x.genres))
                    .country(x.network.country.name)
                    .rating(x.getRating().getAverage())
                    .build();
        }
        movieService.save(movie);
    });

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void createUser(){
        User user = User.builder()
                .email("mert@hotmail.com")
                .name("Mert")
                .surname("Kaya")
                .password("123").phone("123")
                .favGenres(genreService.createGenresWithNames(List.of("Drama", "Science-Fiction", "Horror","Documentary")))
                .favMovies(List.of(1L, 10L, 15L, 100L, 50L, 90L, 120L, 148L))
                .build();
        //////////////////User1/////////////////////////
        User user1 = User.builder()
                .email("merve@gmail.com")
                .name("Merve")
                .favGenres(genreService.createGenresWithNames(List.of("Drama", "Action", "Romance")))
                .favMovies(List.of(8l, 3L, 17L, 18L, 9L, 85L, 78L, 127L, 1L, 120L, 85L))
                .surname("Ustun").password("123").phone("123")
                .build();
        List<MovieComment> movieComments1=List.of(
                MovieComment.builder().content("iyi").date(LocalDate.now().minusYears(1)).userId(2L).movieId(78L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusMonths(3)).userId(2L).movieId(120L).build(),
                MovieComment.builder().content("cok iyi").date(LocalDate.now().minusWeeks(2)).userId(2L).movieId(1L).build(),
                MovieComment.builder().content("idare eder").date(LocalDate.now().minusDays(4)).userId(2L).movieId(136L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusDays(2)).userId(2L).movieId(85L).build()
        );
        movieCommentService.saveAll(movieComments1);
        user1.setComments(movieComments1.stream().map(x->x.getId()).toList());


        //////////////////User2/////////////////////////
        User user2 = User.builder()
                .email("hasan@yandex.com")
                .name("Hasan")
                .surname("Bayindir").password("123").phone("123")
                .favGenres(genreService.createGenresWithNames(List.of("War", "Western", "History", "Action")))
                .favMovies(List.of(152l, 2L, 5L, 88L, 120L, 3L, 120L, 150L, 16L, 25L, 38L, 78L, 96L, 136L))
                .build();
        List<MovieComment> movieComments2=List.of(
                MovieComment.builder().content("iyi").date(LocalDate.now().minusMonths(8)).userId(3l).movieId(2L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusMonths(5)).userId(3l).movieId(136L).build(),
                MovieComment.builder().content("cok iyi").date(LocalDate.now().minusMonths(4)).userId(3l).movieId(106L).build(),
                MovieComment.builder().content("idare eder").date(LocalDate.now().minusWeeks(10)).userId(3l).movieId(26L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusWeeks(2)).userId(3l).movieId(25L).build()
        );
        movieCommentService.saveAll(movieComments2);
        user2.setComments(movieComments2.stream().map(x->x.getId()).toList());

        //////////////////User3/////////////////////////
        User user3 = User.builder()
                .email("aras@gmail.com")
                .favGenres(genreService.createGenresWithNames(List.of("Anime", "Comedy", "Supernatural", "Action")))
                .favMovies(List.of(15l, 2L, 1L, 86L, 39L, 32L, 200L, 15L, 106L, 205L, 138L, 48L, 64L, 136L))
                .name("Aras")
                .surname("Gr").password("123").phone("123").favGenres(genreService.createGenresWithNames(List.of("Anime", "Comedy", "Supernatural", "Action"))).build();
        List<MovieComment> movieComments3=List.of(
                MovieComment.builder().content("berbat").date(LocalDate.now().minusWeeks(58)).userId(4L).movieId(14L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusWeeks(45)).userId(4L).movieId(136L).build(),
                MovieComment.builder().content("cok iyi").date(LocalDate.now().minusMonths(2)).userId(4L).movieId(106L).build(),
                MovieComment.builder().content("berbat").date(LocalDate.now().minusWeeks(3)).userId(4L).movieId(10L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusDays(9)).userId(4L).movieId(15L).build()
        );
        movieCommentService.saveAll(movieComments3);
        user3.setComments(movieComments3.stream().map(x->x.getId()).toList());
        //////////////////User4/////////////////////////
        User user4 = User.builder()
                .favMovies(List.of(51l, 212L, 81L, 86L, 139L, 52L, 20L, 105L, 126L, 25L, 18L, 4L, 6L, 126L))
                .email("didem@gmail.com").name("Didem")
                .surname("Sonmez").password("123").phone("123")
                .favGenres(genreService.createGenresWithNames(List.of("Anime", "Action", "Mystery", "Supernatural"))).build();

        List<MovieComment> movieComments4=List.of(
                MovieComment.builder().content("cok iyi").date(LocalDate.now().minusWeeks(3)).userId(5L).movieId(51L).build(),
                MovieComment.builder().content("�ahane").date(LocalDate.now().minusDays(12)).userId(5L).movieId(105L).build(),
                MovieComment.builder().content("idare eder").date(LocalDate.now().minusDays(3)).userId(5L).movieId(1L).build()
        );
        movieCommentService.saveAll(movieComments4);
        user4.setComments(movieComments4.stream().map(x->x.getId()).toList());
        //////////////////User5/////////////////////////
        User user5 = User.builder()
                .email("admin@gmail.com")
                .name("Mahmut")
                .surname("Hoca").userType(EUserType.ADMIN).password("admin").phone("123")
                .favGenres(genreService.createGenresWithNames(List.of("Science-Fiction", "Drama", "Music")))
                .favMovies(List.of(14l, 22L, 106L, 88L, 104L, 66L, 20L, 104L, 126L, 25L, 13L, 4L, 69L, 47L, 200L))

                .build();

        List<MovieComment> movieComments5=List.of(
                MovieComment.builder().content("cok iyi").date(LocalDate.now().minusWeeks(50)).userId(6L).movieId(14L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusMonths(10)).userId(6L).movieId(66L).build(),
                MovieComment.builder().content("berbat").date(LocalDate.now().minusDays(50)).userId(6L).movieId(10L).build(),
                MovieComment.builder().content("berbat").date(LocalDate.now().minusWeeks(5)).userId(6L).movieId(108L).build(),
                MovieComment.builder().content("iyi").date(LocalDate.now().minusDays(10)).userId(6L).movieId(25L).build()
        );
        movieCommentService.saveAll(movieComments5);
        user5.setComments(movieComments5.stream().map(x->x.getId()).toList());
        userService.saveAll(List.of(user, user1, user2, user3, user4, user5));
    }

    public void setMovieCommentsByMovieId(){
        movieCommentService.findAll().stream().forEach(x->{
            Movie movie = movieService.findById(x.getMovieId()).get();
            movie.getComments().add(x.getId());
            movieService.save(movie);
        });
    }
}
