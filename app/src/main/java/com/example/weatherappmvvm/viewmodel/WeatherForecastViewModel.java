package com.example.weatherappmvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappmvvm.model.WeatherForecastModel;
import com.example.weatherappmvvm.retrofit.RetrofitClient;
import com.example.weatherappmvvm.retrofit.WeatherService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherForecastViewModel extends ViewModel {

    private MutableLiveData<WeatherForecastModel> forecasts;
    private WeatherService weatherService = RetrofitClient.getRetrofitInstance().create(WeatherService.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void loadForecasts(String city) {

        Disposable disposable = weatherService.getWeatherForecast(city, "metric", "26fbb994d78d012c388d5ecb2f45f701")
                .subscribeOn(Schedulers.io())
                .map(weatherForecastModel -> weatherForecastModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherForecastModel1 -> forecasts.setValue(weatherForecastModel1), this::handleError);
        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable t) {
        Log.v("Error", t.getMessage());
    }

    public LiveData<WeatherForecastModel> getForecasts(String city) {
        if (forecasts == null) {
            forecasts = new MutableLiveData<>();
            loadForecasts(city);
        }
        return forecasts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
