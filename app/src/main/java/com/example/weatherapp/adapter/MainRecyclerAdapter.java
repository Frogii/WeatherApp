package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.DayItemBinding;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.util.AppDateUtils;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.WeatherViewHolder> {

    ClickableMainRecycler clickableMainRecycler;

    public MainRecyclerAdapter(ClickableMainRecycler clickableMainRecycler) {
        this.clickableMainRecycler = clickableMainRecycler;
    }

    List<DayTempForecast> data = new ArrayList<>();

    public void setData(List<DayTempForecast> response) {
        this.data = response;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(DayItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
//        Date date = AppDateUtils.convertLongToDate(data.get(position).getDt());
//        holder.day.setText(AppDateUtils.longDateToPattern(date, AppDateUtils.day));
//        holder.temp.setText(String.valueOf((int)Math.round(data.get(position).getMain().getTemp())));
        holder.binding.textViewDayName.setText(AppDateUtils
                .longDateToPattern(data.get(position).getTime(), AppDateUtils.dayNum_day_month));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickableMainRecycler.onItemClick(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        DayItemBinding binding;

        public WeatherViewHolder(@NonNull DayItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
