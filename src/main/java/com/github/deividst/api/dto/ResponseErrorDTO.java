package com.github.deividst.api.dto;

import java.time.LocalDateTime;

public record ResponseErrorDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}
