package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventDtoRq {
    private BigDecimal price;
    private LocalDate date;
    private String eventName;
    private Integer placeId;
    private Integer eventTypeId;
}
