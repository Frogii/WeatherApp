package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.local.WeatherForecast;
import com.example.weatherapp.util.AppDateUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailsRecAdapter extends RecyclerView.Adapter<DetailsRecAdapter.DetailsViewHolder> {

    List<WeatherForecast> data = new ArrayList<>();

    public void setData(List<WeatherForecast> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.details_item, parent, false)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        holder.time.setText(AppDateUtils.longDateToPattern(data.get(position).getTime(), AppDateUtils.hours_minutes));
        holder.temp.setText(String.valueOf((int) data.get(position).getTemp()));
        holder.minTemp.setText(Double.toString(data.get(position).getTempMin()));
        holder.maxTemp.setText(Double.toString(data.get(position).getTempMax()));
        holder.pressure.setText(String.valueOf(data.get(position).getPressure()));
        holder.feels.setText(String.valueOf((int) data.get(position).getFeelsLike()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {

        TextView temp;
        TextView minTemp;
        TextView maxTemp;
        TextView pressure;
        TextView feels;
        TextView time;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.textViewTemp);
            minTemp = itemView.findViewById(R.id.textViewMinTemp);
            maxTemp = itemView.findViewById(R.id.textViewMaxTemp);
            pressure = itemView.findViewById(R.id.textViewPressure);
            feels = itemView.findViewById(R.id.textViewFeels);
            time = itemView.findViewById(R.id.textViewTime);
        }
    }
}
