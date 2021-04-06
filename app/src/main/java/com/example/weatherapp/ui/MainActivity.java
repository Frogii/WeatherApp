package com.example.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.ClickableMainRecycler;
import com.example.weatherapp.adapter.MainRecyclerAdapter;
import com.example.weatherapp.adapter.MainViewPagerAdapter;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.databinding.FragmentMainBinding;
import com.example.weatherapp.db.WeatherDatabase;
import com.example.weatherapp.model.local.DayTempForecast;
import com.example.weatherapp.repository.WeatherRepository;

public class MainActivity extends AppCompatActivity {

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

        drawerSwitch = (SwitchCompat) mainBinding.navView.getMenu().findItem(R.id.nav_switch).getActionView();

        WeatherDatabase db = WeatherDatabase.getInstance(this);
        weatherRepository = new WeatherRepository(db);
        mainViewModelProviderFactory = new MainViewModelProviderFactory(weatherRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelProviderFactory).get(MainViewModel.class);
        mainViewModel.getData();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainCL, new MainFragment(), "main").commit();


//        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WManager.class)
//                .setConstraints(new Constraints.Builder().setRequiresCharging(true).build())
//                .build();
//        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, workInfo -> {
//                    Log.d("myLog", String.valueOf(workInfo.getState()));
//                }
//        );
//        WorkManager.getInstance(this).enqueue(workRequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}