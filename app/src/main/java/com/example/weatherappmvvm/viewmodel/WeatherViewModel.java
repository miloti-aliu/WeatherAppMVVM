package com.example.weatherappmvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappmvvm.model.WeatherModel;
import com.example.weatherappmvvm.retrofit.RetrofitClient;
import com.example.weatherappmvvm.retrofit.WeatherService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<WeatherModel> weathers;
    private WeatherService weatherService = RetrofitClient.getRetrofitInstance().create(WeatherService.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void loadWeathers() {
        Disposable disposable = weatherService.getWeather("524901,703448,2643743,658226,3183875,2673730,5128581", "metric", "26fbb994d78d012c388d5ecb2f45f701")
                .subscribeOn(Schedulers.io())
                .map(weatherModel -> weatherModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherModel -> weathers.setValue(weatherModel), this::handleError);
        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {
        Log.v("Error", throwable.getMessage());
    }

    public LiveData<WeatherModel> getWeathers() {
        if (weathers == null) {
            weathers = new MutableLiveData<>();
            loadWeathers();
        }
        return weathers;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
