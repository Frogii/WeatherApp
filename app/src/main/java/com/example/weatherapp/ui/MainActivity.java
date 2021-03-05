package com.example.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.ClickableMainRecycler;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.databinding.ActivityMainBinding;
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
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        WeatherDatabase db = WeatherDatabase.getInstance(this);
        weatherRepository = new WeatherRepository();
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);
        mainViewModel.getData();

        setupRecycler();

        Log.d("myLog", "day " + AppDateUtils.longDateToPattern(1614826800L, AppDateUtils.dayNum));

        mainViewModel.weatherLiveData.observe(this, weatherResponse ->
                Log.d("myLog", "main Log " + String.valueOf((int)Math.round(weatherResponse.getList().get(0).getMain().getTemp())))
        );

        mainViewModel.dayTempForecastMutableLiveData.observe(this, dayTempForecasts ->
                adapter.setData(dayTempForecasts)
        );
    }

    private void setupRecycler() {
        adapter = new MainRecyclerAdapter(this);
        recyclerView = binding.mainRecyclerView;
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