package org.javaacademy.afisha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "Действия с событиями",
        description = "Методы по работе с событиями")
public class EventController {
    private final EventService eventService;


    @PostMapping
    @Operation(summary = "Добавление нового события")
    public ResponseEntity addEvent(@RequestBody EventDtoRq body) {
        eventService.createEvent(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Вывод всех событий")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventDto.class)))
    public ResponseEntity<List<EventDto>> findAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAllEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Вывод одного события")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventDto.class)))
    public ResponseEntity<EventDto> findEventById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findEventById(id));
    }
}
