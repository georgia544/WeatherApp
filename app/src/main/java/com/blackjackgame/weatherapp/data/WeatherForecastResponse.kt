package com.blackjackgame.weatherapp.data

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse (
   val forecast:ForecastResponse
    )

data class ForecastResponse(
    val forecastday:List<ForecastDayResponse>
)

data class ForecastDayResponse(
    @SerializedName("date_epoch")
    val date:Long,
    val day:DayResponse,
    val hour:List<HourResponse>
)
data class DayResponse(
    @SerializedName("avgtemp_c")
    val temperature:String,
    @SerializedName("maxwind_kph")
    val wind:String,
    @SerializedName("totalprecip_mm")
    val rainfall:String,
    @SerializedName("condition")
    val image:ConditionDayResponse,
    @SerializedName("avghumidity")
    val humidity:String
)

data class ConditionDayResponse(
    val icon:String
)
data class HourResponse(
    @SerializedName("time_epoch")
    val time:Long,
    @SerializedName("temp_c")
    val temperature: String,
    @SerializedName("condition")
    val image:ConditionHourResponse
)

data class ConditionHourResponse(
    val icon:String
)


