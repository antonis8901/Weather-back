package org.example.weather.dto.services;

import org.example.weather.dto.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.example.weather.dto.repositories.WeatherRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final WebClient webClient = WebClient.create("https://api.open-meteo.com");

    @Value("${weather.latitude:37.98}") //
    private double latitude;

    @Value("${weather.longitude:23.73}") // set to Athens
    private double longitude;

    @SuppressWarnings("unchecked")
    public List<WeatherResponse> getTodayHourlyWeather() {
        Map<String, Object> response = webClient.get()
                .uri("/v1/forecast?latitude={lat}&longitude={lon}&hourly=temperature_2m,weathercode&timezone=auto&forecast_days=1",
                        latitude, longitude)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> hourly = (Map<String, Object>) response.get("hourly");
        List<String> times = (List<String>) hourly.get("time");
        List<Number> temperatures = (List<Number>) hourly.get("temperature_2m");
        List<Number> weatherCodes = (List<Number>) hourly.get("weathercode");

        String today = LocalDate.now().toString();
        List<WeatherResponse> result = new ArrayList<>();

        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).startsWith(today)) {
                result.add(new WeatherResponse(
                        times.get(i).substring(11),
                        temperatures.get(i).doubleValue(),
                        getWeatherDescription(weatherCodes.get(i).intValue())
                ));
            }
        }
        return result;
    }

    private String getWeatherDescription(int code) {
        return switch (code) {
            case 0 -> "Clear sky";
            case 1 -> "Mainly clear";
            case 2 -> "Partly cloudy";
            case 3 -> "Overcast";
            case 45, 48 -> "Foggy";
            case 51, 53, 55 -> "Drizzle";
            case 61, 63, 65 -> "Rain";
            case 71, 73, 75 -> "Snow";
            case 77 -> "Snow grains";
            case 80, 81, 82 -> "Rain showers";
            case 85, 86 -> "Snow showers";
            case 95 -> "Thunderstorm";
            case 96, 99 -> "Thunderstorm with hail";
            default -> "Unknown";
        };
    }
}