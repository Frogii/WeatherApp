package com.example.weatherapp.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.model.local.WeatherForecast;
import com.example.weatherapp.model.network.ListItem;
import com.example.weatherapp.model.network.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;
import com.example.weatherapp.util.AppDateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainViewModel extends ViewModel {

    private Disposable apiDisposable;
    private Disposable localDisposable;
    private WeatherRepository repository;
    MutableLiveData<List<DayTempForecast>> dayTempForecastMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> switchButtonState = new MutableLiveData<>(false);

    MainViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    void getData() {
        apiDisposable = repository.getWeatherFromApi()
                .subscribe(response -> {
                    repository.deleteWeatherData().subscribe();
                    List<DayTempForecast> dataList = createDayTempForecastList(response);
                    dayTempForecastMutableLiveData.postValue(dataList);
                    addWeatherToDB(dataList);
                }, error -> {
                    Log.d("myLog", "Network connection ERROR: " + error.getMessage());
                    getWeatherDataFromDB();
                });

    }

    void getWeatherDataFromDB() {
        localDisposable = repository.getWeatherDataFromDB().subscribe(data -> {
                    if (data.isEmpty()) {
                        Log.d("myLog", "Local DB is empty");
                    }
                    Log.d("myLog", data.toString());
                    dayTempForecastMutableLiveData.postValue(data);
                },
                dbError -> Log.d("myLog", "Local DB error")
        );
    }

    void addWeatherToDB(List<DayTempForecast> list) {
        repository.addWeatherData(list).subscribe();
    }

    private List<DayTempForecast> createDayTempForecastList(WeatherResponse response) {
        List<DayTempForecast> list = new ArrayList<>();
        list.add(new DayTempForecast(response.getList().get(0).getDt(), new ArrayList<WeatherForecast>()));
        list.get(0).getWeatherForecasts().add(listItemToWeatherForecast(response.getList().get(0)));
        for (int i = 0; i < response.getList().size()-1; i++) {
            if (!AppDateUtils.longDateToPattern(response.getList().get(i).getDt(), AppDateUtils.dayNum)
                    .equals(AppDateUtils.longDateToPattern(response.getList().get(i + 1).getDt(), AppDateUtils.dayNum))) {
                list.add(new DayTempForecast(response.getList().get(i+1).getDt(), new ArrayList<>()));
            }
            list.get(list.size()-1).getWeatherForecasts().add(listItemToWeatherForecast(response.getList().get(i+1)));
        }
        return list;
    }

    private WeatherForecast listItemToWeatherForecast(ListItem listItem) {
        return new WeatherForecast(listItem.getDt(),
                listItem.getMain().getTemp(),
                listItem.getMain().getTempMin(),
                listItem.getMain().getPressure(),
                listItem.getMain().getFeelsLike(),
                listItem.getMain().getTempMax(),
                listItem.getWeather().get(0).getIcon(),
                listItem.getWeather().get(0).getDescription());
    }

    public void setSwitchButtonState(Boolean state) {
        switchButtonState.setValue(state);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        apiDisposable.dispose();
        if (localDisposable != null) {
            localDisposable.dispose();
        }
    }
}
