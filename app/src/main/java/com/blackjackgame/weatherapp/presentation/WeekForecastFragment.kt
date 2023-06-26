package com.blackjackgame.weatherapp.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.blackjackgame.weatherapp.R

class WeekForecastFragment: Fragment(R.layout.fragment_next_7_days) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    val listDailyItems = arrayListOf(
        WeatherDailyItem(R.drawable.weather_overcast,"12C",DayOfTheWeek.MONDAY),
        WeatherDailyItem(R.drawable.weather_overcast,"12C",DayOfTheWeek.TUESDAY),
        WeatherDailyItem(R.drawable.weather_overcast,"12C",DayOfTheWeek.WEDNESDAY),
        WeatherDailyItem(R.drawable.weather_overcast,"12C",DayOfTheWeek.THURSDAY),
        WeatherDailyItem(R.drawable.weather_overcast,"12C",DayOfTheWeek.FRIDAY),
        WeatherDailyItem(R.drawable.weather_overcast,"12C",DayOfTheWeek.SATURDAY)

    )

    val weatherDailyAdapter = WeatherDailyAdapter(listDailyItems)
    val weatherDailyContainer: RecyclerView = view.findViewById(R.id.recyclerViewWeatherDaily)
    weatherDailyContainer.adapter = weatherDailyAdapter
}
}