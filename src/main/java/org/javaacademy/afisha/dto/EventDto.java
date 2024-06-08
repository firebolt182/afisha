package org.javaacademy.afisha.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {
    private Integer id;
    private String name;
    private Integer eventTypeId;
    private Integer placeId;
    private LocalDate event_date;
}
