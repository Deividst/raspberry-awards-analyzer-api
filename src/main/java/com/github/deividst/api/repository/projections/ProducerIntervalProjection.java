package com.github.deividst.api.repository.projections;

public record ProducerIntervalProjection(
        String producer,
        int firstYear,
        int lastYear,
        int diffYear
) {
}
