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
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.ClickableMainRecycler;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.db.WeatherDatabase;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.model.local.WeatherForecast;
import com.example.weatherapp.model.network.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;
import com.example.weatherapp.util.AppDateUtils;
import com.example.weatherapp.wmanager.WManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

        WeatherDatabase db = WeatherDatabase.getInstance(this);
        weatherRepository = new WeatherRepository(db);
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);
        mainViewModel.getData();

        setupRecycler();

        mainViewModel.dayTempForecastMutableLiveData.observe(this, dayTempForecasts ->
                adapter.setData(dayTempForecasts)
        );

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WManager.class)
                .setConstraints(new Constraints.Builder().setRequiresCharging(true).build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, workInfo -> {
                    Log.d("myLog", String.valueOf(workInfo.getState()));
                }
        );
        WorkManager.getInstance(this).enqueue(workRequest);
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