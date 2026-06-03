package org.example.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherResponse {
    private String time;
    private double temperature;
    private String description;
}