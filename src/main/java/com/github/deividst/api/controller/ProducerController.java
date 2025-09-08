package com.github.deividst.api.controller;

import com.github.deividst.api.dto.ProducerResponseDTO;
import com.github.deividst.api.dto.ResponseErrorDTO;
import com.github.deividst.api.service.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/producers")
public class ProducerController {

    private final ProducerService producerService;

    @Operation
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao realizar analise de intervalos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProducerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar analise de intervalos.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class)))
    })
    @GetMapping("/awards/intervals")
    public ResponseEntity<ProducerResponseDTO> analyzePrizeRange() {
        return ResponseEntity.ok(producerService.analyzePrizeRange());
    }

}
