package com.example.weatherappmvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappmvvm.model.WeatherForecastModel;
import com.example.weatherappmvvm.retrofit.RetrofitClient;
import com.example.weatherappmvvm.retrofit.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherForecastViewModel extends ViewModel {

    private MutableLiveData<WeatherForecastModel> forecasts;
    private WeatherService weatherService = RetrofitClient.getRetrofitInstance().create(WeatherService.class);

    private void loadForecasts(String city) {
        Call<WeatherForecastModel> call =
                weatherService.getWeatherForecast(city, "metric", "26fbb994d78d012c388d5ecb2f45f701");

        call.enqueue(new Callback<WeatherForecastModel>() {
            @Override
            public void onResponse(Call<WeatherForecastModel> call, Response<WeatherForecastModel> response) {
                forecasts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherForecastModel> call, Throwable t) {
                Log.v("Error", t.getMessage());
            }
        });
    }

    public LiveData<WeatherForecastModel> getForecasts(String city){
        if (forecasts == null){
            forecasts = new MutableLiveData<>();
            loadForecasts(city);
        }
        return forecasts;
    }
}
