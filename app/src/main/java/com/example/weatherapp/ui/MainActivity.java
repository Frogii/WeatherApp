package com.example.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.ClickableMainRecycler;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.model.local.WeatherForecast;
import com.example.weatherapp.model.network.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;
import com.example.weatherapp.util.AppDateUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickableMainRecycler {

    WeatherRepository weatherRepository;
    MainViewModel mainViewModel;
    MainViewModelProviderFactory mainViewModelProviderFactory;
    MainRecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        WeatherDatabase db = WeatherDatabase.getInstance(this);
        weatherRepository = new WeatherRepository();
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);
        mainViewModel.getData();

        setupRecycler();

        Log.d("myLog", "day " + AppDateUtils.longDateToPattern(1614826800L, AppDateUtils.dayNum));

        mainViewModel.weatherLiveData.observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                Log.d("myLog", "main Log " + String.valueOf((int)Math.round(weatherResponse.getList().get(0).getMain().getTemp())));

            }
        });

        mainViewModel.dayTempForecastMutableLiveData.observe(this, new Observer<List<DayTempForecast>>() {
            @Override
            public void onChanged(List<DayTempForecast> dayTempForecasts) {
                adapter.setData(dayTempForecasts);
            }
        });
    }

    private void setupRecycler() {
        adapter = new MainRecyclerAdapter(this);
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(DayTempForecast item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}