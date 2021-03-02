package com.example.weatherapp.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;

import io.reactivex.disposables.Disposable;

public class MainViewModel extends ViewModel {

    Disposable disposable;
    WeatherRepository repository;
    MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();

    MainViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    void getData() {
        disposable = repository.getWeather().subscribe(response ->
                weatherLiveData.postValue(response), error -> Log.d("myLog", "Connection ERROR"));
        Log.d("myLog", weatherLiveData.toString());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
