package com.example.weatherapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.util.Converters;

@Database(
        entities = DayTempForecast.class,
        version = 1
)
@TypeConverters(Converters.class)
public abstract class WeatherDatabase extends RoomDatabase {

    abstract public WeatherDao getWeatherDao();

    private static volatile WeatherDatabase instance;

    public static WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (WeatherDatabase.class) {
                if (instance == null) {
                    instance = createDataBase(context);
                }
            }
        }
        return instance;
    }

    private static WeatherDatabase createDataBase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                WeatherDatabase.class,
                "weather_db")
                .build();
    }

}
