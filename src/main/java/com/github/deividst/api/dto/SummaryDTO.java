package com.github.deividst.api.dto;

public record SummaryDTO(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer fallowingWin
) {
}
