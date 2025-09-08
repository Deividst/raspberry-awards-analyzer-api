package com.github.deividst.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieDTO(
        @JsonProperty("year") Integer year,
        @JsonProperty("title") String title,
        @JsonProperty("studios") String studio,
        @JsonProperty("producers") String producer,
        @JsonProperty("winner") String winner
        ) {
}
