package org.javaacademy.afisha.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Report {
    private String name;
    private String type;
    private Integer countSoldTickets;
    private BigDecimal amountSoldTickets;
}
