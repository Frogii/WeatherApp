package com.example.weatherapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.databinding.DetailsItemBinding;
import com.example.weatherapp.model.local.WeatherForecast;
import com.example.weatherapp.util.AppDateUtils;
import com.example.weatherapp.util.Constants;

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
        return new DetailsViewHolder(DetailsItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        holder.binding.textViewTime.setText(AppDateUtils.longDateToPattern(data.get(position).getTime(), AppDateUtils.hours_minutes));
        holder.binding.textViewTemp.setText(String.valueOf((int) data.get(position).getTemp()));
        holder.binding.textViewMinTemp.setText(Double.toString(data.get(position).getTempMin()));
        holder.binding.textViewMaxTemp.setText(Double.toString(data.get(position).getTempMax()));
        holder.binding.textViewPressure.setText(String.valueOf(data.get(position).getPressure()));
        holder.binding.textViewFeels.setText(String.valueOf((int) data.get(position).getFeelsLike()));
        holder.binding.textViewDescription.setText(data.get(position).getWeatherDescription());
        Glide
                .with(holder.itemView.getContext())
                .load(Constants.imagesUrlStart + data.get(position).getImageName() + Constants.imagesUrlEnd)
                .into(holder.binding.imageViewIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {
        DetailsItemBinding binding;

        public DetailsViewHolder(@NonNull DetailsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
