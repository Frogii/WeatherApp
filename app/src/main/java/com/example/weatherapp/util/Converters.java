package com.example.weatherapp.util;

import androidx.room.TypeConverter;

import com.example.weatherapp.model.local.WeatherForecast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromWeatherListToString(List<WeatherForecast> weatherForecasts) {
        return new Gson().toJson(weatherForecasts);
    }

    @TypeConverter
    public List<WeatherForecast> fromStringToWeatherList(String weather) {
        Type listType = new TypeToken<List<WeatherForecast>>(){}.getType();
        return new  Gson().fromJson(weather, listType);
    }
}
