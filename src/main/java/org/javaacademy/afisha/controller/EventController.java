package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;


    @PostMapping
    public ResponseEntity addEvent(@RequestBody EventDtoRq body) {
        eventService.createEvent(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> findAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> findEventbyId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findEventById(id));
    }
}
