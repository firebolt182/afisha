package org.javaacademy.afisha.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportDto {
    private String name;
    private String type;
    private Integer countSoldTickets;
    private BigDecimal amountSoldTickets;
}
