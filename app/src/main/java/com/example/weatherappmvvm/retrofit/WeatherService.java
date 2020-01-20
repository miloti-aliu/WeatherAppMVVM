package com.example.weatherappmvvm.retrofit;

import com.example.weatherappmvvm.model.WeatherForecastModel;
import com.example.weatherappmvvm.model.WeatherModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("group")
    Observable<WeatherModel> getWeather(@Query("id") String cityId,
                                        @Query("units") String unit,
                                        @Query("appid") String appid);

    @GET("forecast")
    Observable<WeatherForecastModel> getWeatherForecast(@Query("q") String cityName,
                                                                     @Query("units") String unit,
                                                                     @Query("appid") String appid);
}
