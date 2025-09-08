package com.github.deividst.api.runner;

import com.github.deividst.api.dto.MovieDTO;
import com.github.deividst.api.model.Movie;
import com.github.deividst.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialLoader implements CommandLineRunner {

    private static final String CSV_FILE_PATH = "src/main/resources/Movielist.csv";

    private final MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando processamento de carga inicial");
        File csvFile = new File(CSV_FILE_PATH);
        List<MovieDTO> movies = MovieCSVReader.read(csvFile);

        List<Movie> movieEvents = new ArrayList<>();

        movies.forEach(movie -> movieEvents.add(Movie.builder()
                .studio(movie.studio())
                .year(movie.year())
                .winner("yes".equalsIgnoreCase(movie.winner()))
                .producer(movie.producer())
                .title(movie.title())
                .build()));

        movieRepository.saveAll(movieEvents);
        log.info("Finalizado processamento de carga inicial");
    }
}
