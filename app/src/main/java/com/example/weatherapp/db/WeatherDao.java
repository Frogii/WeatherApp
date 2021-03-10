package com.example.weatherapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.weatherapp.model.local.DayTempForecast;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.DELETE;


@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addWeatherData(List<DayTempForecast> response);

    @Query("DELETE FROM weather")
    Completable deleteWeatherData();

    @Query("SELECT * FROM weather")
    Observable<List<DayTempForecast>> getWeatherData();
}
