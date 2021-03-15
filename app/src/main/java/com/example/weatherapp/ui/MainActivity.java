package com.example.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.ClickableMainRecycler;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.adapter.MainViewPagerAdapter;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.databinding.NavHeaderBinding;
import com.example.weatherapp.db.WeatherDatabase;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.repository.WeatherRepository;
import com.example.weatherapp.wmanager.WManager;

public class MainActivity extends AppCompatActivity implements ClickableMainRecycler {

    WeatherRepository weatherRepository;
    MainViewModel mainViewModel;
    MainViewModelProviderFactory mainViewModelProviderFactory;
    MainRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ViewPager2 pagerView;
    MainViewPagerAdapter pagerAdapter;
    ActivityMainBinding mainBinding;
    ActionBarDrawerToggle toggle;
    SwitchCompat drawerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        DrawerLayout mainDrawerLayout = mainBinding.mainDrawerLayout;
        toggle = new ActionBarDrawerToggle(this, mainDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupRecycler();
        setupPager();

        drawerSwitch = (SwitchCompat) mainBinding.navView.getMenu().findItem(R.id.nav_switch).getActionView();

        WeatherDatabase db = WeatherDatabase.getInstance(this);
        weatherRepository = new WeatherRepository(db);
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);
        mainViewModel.getData();

        mainViewModel.dayTempForecastMutableLiveData.observe(this, dayTempForecasts -> {
                    recyclerAdapter.setData(dayTempForecasts);
                    pagerAdapter.setData(dayTempForecasts);
                }
        );

        mainViewModel.switchButtonState.observe(this, state -> {
            drawerSwitch.setChecked(state);
            Log.d("myLog", state.toString());
        });

//        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WManager.class)
//                .setConstraints(new Constraints.Builder().setRequiresCharging(true).build())
//                .build();
//        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, workInfo -> {
//                    Log.d("myLog", String.valueOf(workInfo.getState()));
//                }
//        );
//        WorkManager.getInstance(this).enqueue(workRequest);

        drawerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                drawerSwitch.setText(R.string.pager_on);
                mainBinding.mainRecyclerView.setVisibility(View.GONE);
                mainBinding.mainPagerView.setVisibility(View.VISIBLE);
                mainViewModel.setSwitchButtonState(true);
            } else {
                drawerSwitch.setText(R.string.pager_off);
                mainBinding.mainRecyclerView.setVisibility(View.VISIBLE);
                mainBinding.mainPagerView.setVisibility(View.GONE);
                mainViewModel.setSwitchButtonState(false);
            }
            closeDrawer();
        });
    }

    private void setupRecycler() {
        recyclerAdapter = new MainRecyclerAdapter(this);
        recyclerView = mainBinding.mainRecyclerView;
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupPager() {
        pagerAdapter = new MainViewPagerAdapter(this);
        pagerView = mainBinding.mainPagerView;
        pagerView.setAdapter(pagerAdapter);
    }

    @Override
    public void onItemClick(DayTempForecast item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void closeDrawer() {
        if (mainBinding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}