package com.example.weatherapp.api;

import com.example.weatherapp.db.WeatherDatabase;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static volatile Retrofit instance;

    private static Retrofit getInstance() {
        if (instance == null){
            synchronized (RetrofitInstance.class) {
                if (instance == null){
                    instance = getRetrofit();
                }
            }
        }
        return instance;
    }

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static WeatherAPI api() {
       return getInstance().create(WeatherAPI.class);
    }
}
