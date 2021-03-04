package com.example.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.DetailsRecAdapter;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.model.local.WeatherForecast;
import com.example.weatherapp.model.network.WeatherResponse;

public class DetailsActivity extends AppCompatActivity {

    DetailsRecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DayTempForecast data = (DayTempForecast) getIntent().getSerializableExtra("item");
        setupRecycler();
        adapter.setData(data.getWeatherForecasts());

        
    }

    private void setupRecycler() {
        adapter = new DetailsRecAdapter();
        RecyclerView detailsRecycler = findViewById(R.id.recyclerViewDetails);
        detailsRecycler.setAdapter(adapter);
        detailsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}