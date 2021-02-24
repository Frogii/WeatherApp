package com.example.weatherapp.api;

import com.example.weatherapp.Constants;
import com.example.weatherapp.model.WeatherResponse;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface WeatherAPI {

    @GET("data/2.5/forecast?q=Minsk&cnt=7&units=metric&appid=" + Constants.apiKey)
    Observable<WeatherResponse> getWeatherData();
}
