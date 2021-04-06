package com.example.weatherapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.DetailsRecAdapter;
import com.example.weatherapp.databinding.FragmentDetailsBinding;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.model.local.WeatherForecast;

import java.util.List;

public class DetailsFragment extends Fragment {

    FragmentDetailsBinding fragmentDetailsBinding;
    DetailsRecAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDetailsBinding = FragmentDetailsBinding.bind(view);
        setupRecycler();

        Bundle bundle = this.getArguments();
        DayTempForecast data = (DayTempForecast) bundle.getSerializable("item");
        adapter.setData(data.getWeatherForecasts());

    }

    private void setupRecycler() {
        adapter = new DetailsRecAdapter();
        RecyclerView detailsRecycler = fragmentDetailsBinding.recyclerViewDetails;
        detailsRecycler.setAdapter(adapter);
        detailsRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}