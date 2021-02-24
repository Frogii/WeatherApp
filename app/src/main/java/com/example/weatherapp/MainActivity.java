package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.api.RetrofitInstance;
import com.example.weatherapp.api.WeatherAPI;
import com.example.weatherapp.model.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    WeatherAPI api;
    Retrofit retrofitClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = RetrofitInstance.api();

        getData();

    }

    private void getData() {
        Observable<WeatherResponse> observable = api.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(response ->
                Log.d("myLog", String.valueOf((int)Math.round(response.getList().get(0).getMain().getTemp()))),
                e -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show());
    }
}