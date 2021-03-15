package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.databinding.PagerDayItemBinding;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.util.AppDateUtils;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends RecyclerView.Adapter<MainViewPagerAdapter.ViewPagerViewHolder> {

    ClickableMainRecycler clickableMainPager;
    List<DayTempForecast> data = new ArrayList<>();

    public MainViewPagerAdapter(ClickableMainRecycler clickableMainPager) {
        this.clickableMainPager = clickableMainPager;
    }

    public void setData(List<DayTempForecast> response) {
        this.data = response;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerViewHolder(PagerDayItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        holder.binding.textViewDayNamePager.setText(AppDateUtils
                .longDateToPattern(data.get(position).getTime(), AppDateUtils.dayNum_day_month));
        holder.itemView.setOnClickListener(v ->
                clickableMainPager.onItemClick(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {

        PagerDayItemBinding binding;

        public ViewPagerViewHolder(@NonNull PagerDayItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
