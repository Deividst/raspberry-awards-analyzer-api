package com.github.deividst.api.service.impl;

import com.github.deividst.api.dto.ProducerResponseDTO;
import com.github.deividst.api.dto.SummaryDTO;
import com.github.deividst.api.exceptions.BusinessException;
import com.github.deividst.api.repository.MovieRepository;
import com.github.deividst.api.repository.projections.ProducerIntervalProjection;
import com.github.deividst.api.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

    private final MovieRepository movieRepository;

    public ProducerResponseDTO analyzePrizeRange() {
        List<ProducerIntervalProjection> producerIntervalProjections = movieRepository.findProducerWithAwardInterval();
        List<SummaryDTO> summaryDTOListMin = findProducerWithShorterBetweenAwards(producerIntervalProjections);
        List<SummaryDTO> summaryDTOListMax = findProducerWithLongerBetweenAwards(producerIntervalProjections);

        return new ProducerResponseDTO(summaryDTOListMin, summaryDTOListMax);
    }

    private List<SummaryDTO> findProducerWithShorterBetweenAwards(List<ProducerIntervalProjection> producerIntervals) {
        Integer intervalMin = producerIntervals.stream()
                .min(Comparator.comparingInt(ProducerIntervalProjection::diffYear))
                .map(ProducerIntervalProjection::diffYear)
                .orElseThrow(() -> new BusinessException("Nenhum intervalo minimo encontrado"));

        return producerIntervals.stream()
                .filter(producerInterval -> Objects.equals(producerInterval.diffYear(), intervalMin))
                .map(producerInterval -> new SummaryDTO(
                        producerInterval.producer(),
                        producerInterval.diffYear(),
                        producerInterval.firstYear(),
                        producerInterval.lastYear()
                )).toList();
    }

    private List<SummaryDTO> findProducerWithLongerBetweenAwards(List<ProducerIntervalProjection> producerIntervals) {
        Integer intervalMax = producerIntervals.stream()
                .max(Comparator.comparingInt(ProducerIntervalProjection::diffYear))
                .map(ProducerIntervalProjection::diffYear)
                .orElseThrow(() -> new BusinessException("Nenhum intervalo maximo encontrado"));

        return producerIntervals.stream()
                .filter(producerInterval -> Objects.equals(producerInterval.diffYear(), intervalMax))
                .map(producerInterval -> new SummaryDTO(
                        producerInterval.producer(),
                        producerInterval.diffYear(),
                        producerInterval.firstYear(),
                        producerInterval.lastYear()
                )).toList();
    }
}
