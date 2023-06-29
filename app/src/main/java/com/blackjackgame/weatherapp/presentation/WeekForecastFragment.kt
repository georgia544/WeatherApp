package com.blackjackgame.weatherapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.blackjackgame.weatherapp.R
import com.blackjackgame.weatherapp.data.WeatherApi
import com.bumptech.glide.Glide
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

class WeekForecastFragment: Fragment(R.layout.fragment_next_7_days) {

    private lateinit var currentTemperature: TextView
    private lateinit var currentImage:ImageView
    private lateinit var currentRainfall:TextView
    private lateinit var currentWind:TextView
    private lateinit var currentHumidity:TextView
    private lateinit var weatherDailyAdapter: WeatherDailyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentTemperature = view.findViewById(R.id.temperature_text)
        currentImage = view.findViewById(R.id.image_weather)
        currentRainfall = view.findViewById(R.id.cm)
        currentWind = view.findViewById(R.id.km_h)
        currentHumidity = view.findViewById(R.id.percent)



        weatherDailyAdapter = WeatherDailyAdapter(arrayListOf())
        val weatherDailyContainer: RecyclerView = view.findViewById(R.id.recyclerViewWeatherDaily)
        weatherDailyContainer.adapter = weatherDailyAdapter

        api()
    }

    private fun api() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: WeatherApi = retrofit.create(WeatherApi::class.java)

        currentWeather(api)
        forecastWeather(api)
    }

    private fun currentWeather(api: WeatherApi) {
        Thread {


            val result = api.getForecast("Baku").execute().body()
            val forecastDay = result?.forecast?.forecastday?.get(0)?.day
            val temp = forecastDay?.temperature
            val humidity = forecastDay?.humidity
            val wind = forecastDay?.wind
            val rainfall = forecastDay?.rainfall
            val imageTemp = forecastDay?.image?.icon

            //  Log.e("glide", imageTemp.toString())

            view?.post {
                currentHumidity.text = humidity.toString() + " %"
                currentWind.text = wind.toString() + " km/h"
                currentRainfall.text = rainfall.toString() + " cm"
                currentTemperature.text = temp?.toFloat()?.roundToInt().toString() + " °C"

                Glide
                    .with(this)
                    .load("https:$imageTemp")
                    .into(currentImage);
            }


        }.start()
    }

    private fun forecastWeather(api: WeatherApi) {
        Thread {
            val result = api.getForecast("Baku").execute().body()

            val dayForecast: List<WeatherDailyItem> = result?.forecast?.forecastday
                ?.map { it ->
                    WeatherDailyItem(
                        it.day.image.icon,
                        it.day.temperature.toFloat()?.roundToInt().toString() + " °C"
                        ,it.date)
                }?.filterIndexed{ index, _ ->index!=0  } ?: emptyList()

            view?.post {
                weatherDailyAdapter.updateItems(
                    ArrayList(dayForecast)
                )
            }
        }.start()
    }


}
