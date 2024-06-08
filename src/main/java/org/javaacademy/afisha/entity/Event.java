package org.javaacademy.afisha.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Event {
    private Integer id;
    private String name;
    private Integer eventTypeId;
    private Integer placeId;
    private LocalDate event_date;
}
