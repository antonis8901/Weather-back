package org.example.weather.controllers;

import org.example.weather.dto.response.WeatherResponse;
import org.example.weather.dto.services.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/today")
    public ResponseEntity<List<WeatherResponse>> getTodayWeather() {
        return ResponseEntity.ok(weatherService.getTodayHourlyWeather());
    }
}
