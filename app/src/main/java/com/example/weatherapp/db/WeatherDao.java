package com.example.weatherapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherapp.model.WeatherItem;

import java.util.List;


@Dao
public interface WeatherDao {

//    @Insert
//    Completable addWeatherItems(List<WeatherItem> weatherList);

//    @Query("SELECT * FROM item")
//    Observable<List<WeatherItem>> getWeatherItems();
}
