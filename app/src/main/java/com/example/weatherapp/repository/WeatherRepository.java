package com.example.weatherapp.repository;

import com.example.weatherapp.api.RetrofitInstance;
import com.example.weatherapp.api.WeatherAPI;

import com.example.weatherapp.db.WeatherDatabase;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.model.network.WeatherResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepository {

    private WeatherDatabase db;

    public WeatherRepository(WeatherDatabase db) {
        this.db = db;
    }

    private WeatherAPI api = RetrofitInstance.api();

    public Observable<WeatherResponse> getWeatherFromApi() {
        return api.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable addWeatherData(List<DayTempForecast> list) {
        return db.getWeatherDao()
                .addWeatherData(list)
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<DayTempForecast>> getWeatherDataFromDB() {
        return db.getWeatherDao()
                .getWeatherData()
                .subscribeOn(Schedulers.io());
    }


}
