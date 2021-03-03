package com.example.weatherapp.model.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather")
public class DayTemp {

    @PrimaryKey
    private final Long time;
    private final Double temperature;

    public DayTemp(Long time, Double temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public Long getTime() {
        return time;
    }

    public Double getTemperature() {
        return temperature;
    }
}
