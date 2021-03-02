package com.example.weatherapp.repository;

import com.example.weatherapp.api.RetrofitInstance;
import com.example.weatherapp.api.WeatherAPI;
import com.example.weatherapp.model.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepository {

    private WeatherAPI api = RetrofitInstance.api();

    public Observable<WeatherResponse> getWeather() {
        return api.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
