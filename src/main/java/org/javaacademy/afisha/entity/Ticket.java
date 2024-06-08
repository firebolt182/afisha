package org.javaacademy.afisha.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Ticket {
    private Integer id;
    private Integer eventId;
    private String clientEmail;
    private BigDecimal price;
    private boolean isSold;
}
