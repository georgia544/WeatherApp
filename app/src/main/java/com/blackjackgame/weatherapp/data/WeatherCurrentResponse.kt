package com.blackjackgame.weatherapp.data

import com.google.gson.annotations.SerializedName


data class WeatherCurrentResponse(
    val location: LocationWeatherResponse,
    @SerializedName("current")
    val currentWeather: CurrentWeatherResponse,
)

data class LocationWeatherResponse(
    val country: String,
    @SerializedName("name")
    val city: String,
    @SerializedName("localtime_epoch")
    val date:Long
)

data class CurrentWeatherResponse(
    @SerializedName("temp_c")
    val temp: String,
    @SerializedName("wind_kph")
    val windVelocity: String,
    val humidity: String,
    @SerializedName("precip_mm")
    val rainfall: String,
    @SerializedName("condition")
    val weatherTextAndIcon: CurrentConditionWeatherResponse

)

data class CurrentConditionWeatherResponse(
    val text: String,
    val icon:String
)