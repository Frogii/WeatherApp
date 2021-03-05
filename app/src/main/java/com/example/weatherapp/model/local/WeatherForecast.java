package com.example.weatherapp.model.local;

import java.io.Serializable;

public class WeatherForecast implements Serializable {

    private final long time;
    private final double temp;
    private final double tempMin;
    private final int pressure;
    private final double feelsLike;
    private final double tempMax;
    private final String imageName;
    private final String weatherDescription;

    public WeatherForecast(long time, double temp, double tempMin, int pressure, double feelsLike, double tempMax, String imageName, String weatherDescription) {
        this.time = time;
        this.temp = temp;
        this.tempMin = tempMin;
        this.pressure = pressure;
        this.feelsLike = feelsLike;
        this.tempMax = tempMax;
        this.imageName = imageName;
        this.weatherDescription = weatherDescription;
    }

    public Long getTime() {
        return time;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public int getPressure() {
        return pressure;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public double getTempMax() {
        return tempMax;
    }

    public String getImageName() {
        return imageName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
}
