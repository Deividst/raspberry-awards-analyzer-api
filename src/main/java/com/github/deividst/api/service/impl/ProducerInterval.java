package com.github.deividst.api.service.impl;

import com.github.deividst.api.model.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ProducerInterval {

    private String producer;

    private Integer intervalMin = 0;
    private Integer previousWinMin;
    private Integer fallowingWinMin;

    private Integer intervalMax = 0;
    private Integer previousWinMax;
    private Integer fallowingWinMax;

    public void calculateInterval(Movie movie) {
        if (Objects.isNull(this.producer)) {
            this.producer = movie.getProducer();
            this.previousWinMin = movie.getYear();
            this.previousWinMax = movie.getYear();
        }

        calculateIntervalMin(movie);
        calculateIntervalMax(movie);
    }

    private void calculateIntervalMin(Movie movie) {
        if (movie.getYear() > this.previousWinMin
                && (Objects.isNull(this.fallowingWinMin) || movie.getYear() < this.fallowingWinMin)) {
            this.intervalMin = movie.getYear() - this.previousWinMin;
            this.fallowingWinMin = movie.getYear();
        }
    }

    private void calculateIntervalMax(Movie movie) {
        if (movie.getYear() > this.previousWinMax
                && Objects.isNull(this.fallowingWinMax)) {
            this.intervalMax = movie.getYear() - this.previousWinMax;
            this.fallowingWinMax = movie.getYear();

        } else if (Objects.nonNull(this.fallowingWinMax)
                && movie.getYear() > this.fallowingWinMax) {
            int newInterval = movie.getYear() - this.fallowingWinMax;

            if (newInterval > this.intervalMax) {
                this.previousWinMax = this.fallowingWinMax;
                this.intervalMax = newInterval;
                this.fallowingWinMax = movie.getYear();
            }
        }
    }

}
