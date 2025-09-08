package com.github.deividst.api.service.impl;

import com.github.deividst.api.dto.ProducerResponseDTO;
import com.github.deividst.api.dto.SummaryDTO;
import com.github.deividst.api.exceptions.BusinessException;
import com.github.deividst.api.model.Movie;
import com.github.deividst.api.repository.MovieRepository;
import com.github.deividst.api.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

    private final MovieRepository movieRepository;

    public ProducerResponseDTO analyzePrizeRange() {
        List<Movie> movies = movieRepository.findWithMoreThanOneVictory();

        Map<String, List<Movie>> moviesByProducer = movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getProducer,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));

        List<ProducerInterval> producerIntervals = new ArrayList<>();

        for (List<Movie> movieList : moviesByProducer.values()) {
            ProducerInterval producerInterval = new ProducerInterval();
            movieList.forEach(producerInterval::calculateInterval);
            producerIntervals.add(producerInterval);
        }

        List<SummaryDTO> summaryDTOListMin = findProducerWithShorterBetweenAwards(producerIntervals);
        List<SummaryDTO> summaryDTOListMax = findProducerWithLongerBetweenAwards(producerIntervals);

        return new ProducerResponseDTO(summaryDTOListMin, summaryDTOListMax);
    }

    private List<SummaryDTO> findProducerWithShorterBetweenAwards(List<ProducerInterval> producerIntervals) {
        Integer intervalMin = producerIntervals.stream()
                .min(Comparator.comparingInt(ProducerInterval::getIntervalMin))
                .map(ProducerInterval::getIntervalMin)
                .orElseThrow(() -> new BusinessException("Nenhum intervalo minimo encontrado"));

        return producerIntervals.stream()
                .filter(producerInterval -> Objects.equals(producerInterval.getIntervalMin(), intervalMin))
                .map(producerInterval -> new SummaryDTO(
                        producerInterval.getProducer(),
                        producerInterval.getIntervalMin(),
                        producerInterval.getPreviousWinMin(),
                        producerInterval.getFallowingWinMin()
                )).toList();
    }

    private List<SummaryDTO> findProducerWithLongerBetweenAwards(List<ProducerInterval> producerIntervals) {
        Integer intervalMax = producerIntervals.stream()
                .max(Comparator.comparingInt(ProducerInterval::getIntervalMax))
                .map(ProducerInterval::getIntervalMax)
                .orElseThrow(() -> new BusinessException("Nenhum intervalo maximo encontrado"));

        return producerIntervals.stream()
                .filter(producerInterval -> Objects.equals(producerInterval.getIntervalMax(), intervalMax))
                .map(producerInterval -> new SummaryDTO(
                        producerInterval.getProducer(),
                        producerInterval.getIntervalMax(),
                        producerInterval.getPreviousWinMax(),
                        producerInterval.getFallowingWinMax()
                )).toList();
    }
}
