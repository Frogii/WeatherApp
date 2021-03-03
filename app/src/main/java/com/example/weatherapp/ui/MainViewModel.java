package com.example.weatherapp.ui;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.local.DayTemp;
import com.example.weatherapp.model.network.ListItem;
import com.example.weatherapp.model.network.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    Disposable disposable;
    WeatherRepository repository;
    MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();
    MutableLiveData<Map<Long, Double>> timeTempMap = new MutableLiveData<>();
    List<DayTemp> dayTemps = new ArrayList<>();

    MainViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    void getData() {
        disposable = repository.getWeatherFromApi()
                .subscribe(response -> {
                    weatherLiveData.postValue(response);
                    Map<Long, Double> tempMap = new HashMap<>();
                    for (ListItem item : response.getList()) {
                        tempMap.put(item.getDt(), item.getMain().getTemp());
                        dayTemps.add(new DayTemp(item.getDt(), item.getMain().getTemp()));
                    }
                    timeTempMap.postValue(tempMap);
                    Log.d("myLog", tempMap.toString());
                    repository.addWeatherData(dayTemps).subscribe();
                }, error -> {
                    Log.d("myLog", "Connection ERROR");
                    repository.getWeatherDataFromDB().subscribe(data -> {
                                Log.d("myLog", "LOL");
                            },
                            dbError ->
                            {
                                Log.d("myLog", "NO DATA");
                            }
                    );
                });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
