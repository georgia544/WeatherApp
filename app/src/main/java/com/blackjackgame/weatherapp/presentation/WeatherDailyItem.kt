package com.blackjackgame.weatherapp.presentation

data class WeatherDailyItem(val image: Int, val temperature: String, val day:DayOfTheWeek) {
}
enum class DayOfTheWeek{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}