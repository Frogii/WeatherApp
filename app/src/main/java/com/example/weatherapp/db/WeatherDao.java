package com.example.weatherapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weatherapp.model.local.DayTemp;
import com.example.weatherapp.model.network.WeatherResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addWeatherData(List<DayTemp> response);

    @Query("SELECT * FROM weather")
    Observable<List<DayTemp>> getWeatherData();
}
