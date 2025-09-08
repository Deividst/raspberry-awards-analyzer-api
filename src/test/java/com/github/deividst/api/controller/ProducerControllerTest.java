package com.github.deividst.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.deividst.api.dto.ProducerResponseDTO;
import com.github.deividst.api.dto.ResponseErrorDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProducerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void analyzePrizeRangeTest() throws IOException {
        ProducerResponseDTO expectedResponse = objectMapper.readValue(new File("src/test/resources/mocks/producer_response_200.json"), ProducerResponseDTO.class);

        ProducerResponseDTO response = restTemplate.getForObject(
                "http://localhost:" + port + "/v1/producers/awards/intervals",
                ProducerResponseDTO.class
        );

        assertThat(response).isNotNull();
        Assertions.assertEquals(expectedResponse, response);
    }
}