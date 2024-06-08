package org.javaacademy.afisha.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {
    private Integer id;
    private Integer eventId;
    private String clientEmail;
    private boolean isSold;
}
