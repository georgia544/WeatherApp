package com.blackjackgame.weatherapp.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET ("current.json")
    fun getWeather(
        @Query ("q") city:String,
        @Query("key") key:String = "7eda25738b4d493ca8692830231806",
        @Query ("aqi") airQuality:String = "no"
    ): Call<WeatherCurrentResponse>

    @GET("forecast.json")
    fun getForecast(
        @Query("key") key:String = "7eda25738b4d493ca8692830231806",
        @Query ("q") city:String,
        @Query ("aqi") airQuality:String = "no",
        @Query ("alerts") weatherAlert:String = "no",
        @Query("days") days:String = "7"
    ):Call<WeatherForecastResponse>
}

