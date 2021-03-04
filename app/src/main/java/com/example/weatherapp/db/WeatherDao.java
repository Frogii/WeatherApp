package com.example.weatherapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


//@Dao
//public interface WeatherDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    Completable addWeatherData(List<DayTempForecast> response);
//
//    @Query("SELECT * FROM weather")
//    Observable<List<DayTempForecast>> getWeatherData();
//}
