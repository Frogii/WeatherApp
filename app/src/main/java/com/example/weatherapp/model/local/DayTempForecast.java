package com.example.weatherapp.model.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

//@Entity(tableName = "weather")
public class DayTempForecast implements Serializable {

//    @PrimaryKey
    private final long time;
    List<WeatherForecast> weatherForecasts;

    public DayTempForecast(long time, List<WeatherForecast> weatherForecasts) {
        this.time = time;
        this.weatherForecasts = weatherForecasts;
    }

    public Long getTime() {
        return time;
    }

    public List<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }
}
