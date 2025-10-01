package com.github.deividst.api.runner;

import com.github.deividst.api.dto.MovieDTO;
import com.github.deividst.api.model.Movie;
import com.github.deividst.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialLoader implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando processamento de carga inicial");
        InputStream is = getClass().getResourceAsStream("/Movielist.csv");

        List<MovieDTO> movies = MovieCSVReader.read(is);

        List<Movie> movieEvents = new ArrayList<>();

        movies.forEach(movie -> {
            String producersWithoutAnd = movie.producer().replaceAll(" and ", ",");
            String[] producers = producersWithoutAnd.split(",");

            List<String> producersList = Arrays.stream(producers)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            producersList.forEach(producer -> {
                movieEvents.add(Movie.builder()
                        .studio(movie.studio())
                        .year(movie.year())
                        .winner("yes".equalsIgnoreCase(movie.winner()))
                        .producer(producer)
                        .title(movie.title().trim())
                        .build());
            });
        });

        movieRepository.saveAll(movieEvents);
        log.info("Finalizado processamento de carga inicial");
    }
}
