package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.network.ListItem;
import com.example.weatherapp.util.AppDateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.WeatherViewHolder> {

    List<ListItem> data = new ArrayList<>();

    public void setData(List<ListItem> response) {
        this.data = response;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Date date = AppDateUtils.convertLongToDate(data.get(position).getDt());
        holder.day.setText(AppDateUtils.dateToString(date));
        holder.temp.setText(String.valueOf((int)Math.round(data.get(position).getMain().getTemp())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        final TextView day;
        final TextView temp;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.textViewDayName);
            temp = itemView.findViewById(R.id.textViewTemperature);

        }
    }
}
