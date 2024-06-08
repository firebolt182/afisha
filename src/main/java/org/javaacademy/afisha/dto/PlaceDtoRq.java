package org.javaacademy.afisha.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
public class PlaceDtoRq {
    private String name;
    private String address;
    private String city;
}
