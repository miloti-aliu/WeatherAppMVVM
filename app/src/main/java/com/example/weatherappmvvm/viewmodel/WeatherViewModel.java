package com.example.weatherappmvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappmvvm.model.WeatherModel;
import com.example.weatherappmvvm.retrofit.RetrofitClient;
import com.example.weatherappmvvm.retrofit.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<WeatherModel> weathers;
    private WeatherService weatherService = RetrofitClient.getRetrofitInstance().create(WeatherService.class);

    private void loadWeathers() {
        Call<WeatherModel> call =
                weatherService.getWeather("524901,703448,2643743,658226,3183875,2673730,5128581", "metric", "26fbb994d78d012c388d5ecb2f45f701");

        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
               weathers.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.v("Error", t.getMessage());
            }
        });
    }

    public LiveData<WeatherModel> getWeathers() {
        if (weathers == null){
            weathers = new MutableLiveData<>();
            loadWeathers();
        }
        return weathers;
    }
}
