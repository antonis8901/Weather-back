package org.example.weather.dto.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Weather {
    private Long id;
    private String weather;
    private String temperature;
    private String humidity;
}
