package com.example.weatherapp.api;

import com.example.weatherapp.util.Constants;
import com.example.weatherapp.model.network.WeatherResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WeatherAPI {

    @GET("data/2.5/forecast?q=Minsk&units=metric&appid=" + Constants.apiKey)
    Observable<WeatherResponse> getWeatherData();
}
