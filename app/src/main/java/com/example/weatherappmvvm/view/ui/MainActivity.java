package com.example.weatherappmvvm.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weatherappmvvm.R;
import com.example.weatherappmvvm.model.WeatherModel;
import com.example.weatherappmvvm.view.adapter.WeatherRecyclerViewAdapter;
import com.example.weatherappmvvm.viewmodel.WeatherViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvCities)RecyclerView recyclerView;

    private WeatherViewModel weatherViewModel;
    private WeatherRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getWeathers().observe(this, weatherModel -> {
            mAdapter = new WeatherRecyclerViewAdapter();
            mAdapter.addWeatherModel(weatherModel);
            bindRecyclerView();
        });
    }

    private void bindRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }
}
