package com.github.deividst.api.runner;

import com.github.deividst.api.dto.MovieDTO;
import com.github.deividst.api.model.AwardEvent;
import com.github.deividst.api.model.Movie;
import com.github.deividst.api.model.Producer;
import com.github.deividst.api.repository.AwardEventRepository;
import com.github.deividst.api.repository.ProducerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialLoader implements CommandLineRunner {

    private static final String CSV_FILE_PATH = "src/main/resources/Movielist.csv";

    private final ProducerRepository producerRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        File csvFile = new File(CSV_FILE_PATH);
        List<MovieDTO> movies = MovieCSVReader.read(csvFile);
        Map<String, List<MovieDTO>> moviesByProducer = new HashMap<>();

        List<AwardEvent> awardEvents = new ArrayList<>();

        movies.forEach(movieDTO -> {
            if (moviesByProducer.containsKey(movieDTO.producer())) {
                moviesByProducer.get(movieDTO.producer()).add(movieDTO);
            } else {
                List<MovieDTO> movieList = new ArrayList<>();
                movieList.add(movieDTO);
                moviesByProducer.put(movieDTO.producer(), movieList);
            }

            if (awardEvents.stream().noneMatch(awardEvent -> Objects.equals(awardEvent.getYear(), movieDTO.year()))) {
                awardEvents.add(AwardEvent.builder()
                        .year(movieDTO.year())
                        .build());
            }
        });

        for (String producerName : moviesByProducer.keySet()) {
            Set<Movie> movieToSave = new HashSet<>();

            Producer producerToSave = Producer.builder()
                    .name(producerName)
                    .build();

            moviesByProducer.get(producerName).forEach(movieDTO -> {
                AwardEvent awardEvent = awardEvents.stream().filter(event -> Objects.equals(event.getYear(), movieDTO.year()))
                        .findFirst()
                        .get();

                    Movie movie = Movie.builder()
                            .title(movieDTO.title())
                            .studio(movieDTO.studio())
                            .producer(producerToSave)
                            .awardEvent(awardEvent)
                            .winner("yes".equalsIgnoreCase(movieDTO.winner()))
                            .build();

                    movieToSave.add(movie);
                });

            producerToSave.setMovie(movieToSave);
            producerRepository.save(producerToSave);
        }
    }
}
