package org.javaacademy.afisha.controller;

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
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity addPlace(@RequestBody PlaceDtoRq body) {
        placeService.addPlace(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> findAllPlaces() {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.findAllPlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> findPlaceById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(placeService.findPlaceById(id));
    }
}
