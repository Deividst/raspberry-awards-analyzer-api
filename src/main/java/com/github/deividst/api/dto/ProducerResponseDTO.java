package com.github.deividst.api.dto;

import java.util.List;

public record ProducerResponseDTO(
        List<SummaryDTO> min,
        List<SummaryDTO> max
) {
}
