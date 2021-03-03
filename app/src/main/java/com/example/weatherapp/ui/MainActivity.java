package com.example.weatherapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.db.WeatherDatabase;
import com.example.weatherapp.model.network.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    WeatherRepository weatherRepository;
    MainViewModel mainViewModel;
    MainViewModelProviderFactory mainViewModelProviderFactory;
    MainRecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherDatabase db = WeatherDatabase.getInstance(this);
        weatherRepository = new WeatherRepository(db);
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);
        mainViewModel.getData();
        observeData();

        setupRecycler();

        mainViewModel.weatherLiveData.observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                Log.d("myLog", "main Log " + String.valueOf((int)Math.round(weatherResponse.getList().get(0).getMain().getTemp())));
                adapter.setData(weatherResponse.getList());
            }
        });
    }

    private void setupRecycler() {
        adapter = new MainRecyclerAdapter();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeData() {
        mainViewModel.timeTempMap.observe(this, new Observer<Map<Long, Double>>() {
                    @Override
                    public void onChanged(Map<Long, Double> longDoubleMap) {
                        Log.d("myLog", "map" + longDoubleMap);
                    }
                }
        );
    }
}