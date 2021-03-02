package com.example.weatherapp.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.repository.WeatherRepository;

public class MainViewModelProviderFactory implements ViewModelProvider.Factory {

    WeatherRepository repository;

    MainViewModelProviderFactory(WeatherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(repository);
    }
}
