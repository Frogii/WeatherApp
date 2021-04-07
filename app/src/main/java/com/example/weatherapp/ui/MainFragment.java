package com.example.weatherapp.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.ClickableMainRecycler;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.adapter.MainViewPagerAdapter;
import com.example.weatherapp.databinding.FragmentMainBinding;
import com.example.weatherapp.model.local.DayTempForecast;


public class MainFragment extends Fragment implements ClickableMainRecycler {

    MainViewModel mainViewModel;
    FragmentMainBinding fragmentMainBinding;
    MainRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ViewPager2 pagerView;
    MainViewPagerAdapter pagerAdapter;
    SwitchCompat drawerSwitch;
    MainActivity activity;
    DetailsFragment detailsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentMainBinding = FragmentMainBinding.bind(view);

        activity = (MainActivity) getActivity();
        mainViewModel = activity.mainViewModel;

        drawerSwitch = (SwitchCompat) activity.mainBinding.navView.getMenu().findItem(R.id.nav_switch).getActionView();

        setupRecycler();
        setupPager();

        mainViewModel.dayTempForecastMutableLiveData.observe(this, dayTempForecasts -> {
                    recyclerAdapter.setData(dayTempForecasts);
                    pagerAdapter.setData(dayTempForecasts);
                }
        );

        mainViewModel.switchButtonState.observe(this, state -> {
            drawerSwitch.setChecked(state);
            Log.d("myLog", state.toString());
        });

        drawerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                drawerSwitch.setText(R.string.pager_on);
                fragmentMainBinding.mainRecyclerView.setVisibility(View.GONE);
                fragmentMainBinding.mainPagerView.setVisibility(View.VISIBLE);
                mainViewModel.setSwitchButtonState(true);
            } else {
                drawerSwitch.setText(R.string.pager_off);
                fragmentMainBinding.mainRecyclerView.setVisibility(View.VISIBLE);
                fragmentMainBinding.mainPagerView.setVisibility(View.GONE);
                mainViewModel.setSwitchButtonState(false);
            }
            closeDrawer();
        });
    }

    private void setupRecycler() {
        recyclerAdapter = new MainRecyclerAdapter(this);
        recyclerView = fragmentMainBinding.mainRecyclerView;
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentMainBinding.getRoot().getContext()));
    }

    private void setupPager() {
        pagerAdapter = new MainViewPagerAdapter(this);
        pagerView = fragmentMainBinding.mainPagerView;
        pagerView.setAdapter(pagerAdapter);
    }

    @Override
    public void onItemClick(DayTempForecast item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().add(R.id.mainCL, detailsFragment).addToBackStack("details").commit();
    }

    void closeDrawer() {
        if (activity.mainBinding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            activity.mainBinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}