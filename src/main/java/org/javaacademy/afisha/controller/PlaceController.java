package org.javaacademy.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.dto.PlaceDtoRq;
import org.javaacademy.afisha.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
@Tag(name = "Действия с местами",
        description = "Методы по работе с местами")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    @Operation(summary = "Создание места")
    public ResponseEntity addPlace(@RequestBody PlaceDtoRq body) {
        placeService.addPlace(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Вывод всех мест")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaceDto.class)))
    public ResponseEntity<List<PlaceDto>> findAllPlaces() {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.findAllPlaces());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Вывод определенного места по айди")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaceDto.class)))
    public ResponseEntity<PlaceDto> findPlaceById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.findPlaceById(id));
    }
}
