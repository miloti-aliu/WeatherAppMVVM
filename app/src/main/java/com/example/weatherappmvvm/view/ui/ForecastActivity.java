package com.example.weatherappmvvm.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappmvvm.R;
import com.example.weatherappmvvm.view.adapter.WeatherForecastRecyclerViewAdapter;
import com.example.weatherappmvvm.viewmodel.WeatherForecastViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastActivity extends AppCompatActivity {

    @BindView(R.id.rvForecasts)RecyclerView recyclerView;

    private WeatherForecastViewModel weatherForecastViewModel;
    private WeatherForecastRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String city = intent.getStringExtra("cityName");

        weatherForecastViewModel = ViewModelProviders.of(this).get(WeatherForecastViewModel.class);
        weatherForecastViewModel.getForecasts(city).observe(this, weatherForecastModel -> {
            mAdapter = new WeatherForecastRecyclerViewAdapter();
            mAdapter.addWeatherForecastModel(weatherForecastModel);
            bindRecyclerView();
        });
    }

    private void bindRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }
}
