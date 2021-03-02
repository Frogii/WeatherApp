package com.example.weatherapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.R;
import com.example.weatherapp.api.RetrofitInstance;
import com.example.weatherapp.api.WeatherAPI;
import com.example.weatherapp.model.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    WeatherAPI api;
    WeatherRepository weatherRepository;
    MainViewModel mainViewModel;
    MainViewModelProviderFactory mainViewModelProviderFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherRepository = new WeatherRepository();
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);

        mainViewModel.getData();

        mainViewModel.weatherLiveData.observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                Log.d("myLog", "main Log " + String.valueOf((int)Math.round(weatherResponse.getList().get(0).getMain().getTemp())));
            }
        });

    }
}