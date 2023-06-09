package com.blackjackgame.weatherapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var humidityPercentage: TextView
    private lateinit var windVelocity: TextView
    private lateinit var rainFall: TextView
    private lateinit var weatherTemperature: TextView
    private lateinit var weatherText: TextView
    private lateinit var weatherImage: ImageView
    private lateinit var location: TextView
    private lateinit var dateWeather: TextView
    private lateinit var weatherHourlyAdapter: WeatherHourlyAdapter
    private lateinit var tomorrowButton:TextView
    private lateinit var todayButton:TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button_next7days)
            .setOnClickListener {
                parentFragmentManager.commit {
                    addToBackStack("home")
                    replace<WeekForecastFragment>(R.id.fragment_container_view)
                }
            }

        humidityPercentage = view.findViewById(R.id.humidity_percentage)
        windVelocity = view.findViewById(R.id.wind_velocity)
        rainFall = view.findViewById(R.id.rainFall_cm)
        weatherTemperature = view.findViewById(R.id.weather_temperature)
        weatherText = view.findViewById(R.id.weather_text)
        weatherImage = view.findViewById(R.id.weather_image)
        location = view.findViewById(R.id.country_city)
        dateWeather = view.findViewById(R.id.date)

        val seekbar = view.findViewById<SeekBar>(R.id.seekBar)

        tomorrowButton = view.findViewById(R.id.button_tomorrow)
        todayButton = view.findViewById(R.id.button_today)


        weatherHourlyAdapter = WeatherHourlyAdapter(arrayListOf())
        val weatherHourlyContainer: RecyclerView = view.findViewById(R.id.recyclerViewWeatherHourly)
        weatherHourlyContainer.adapter = weatherHourlyAdapter



        seekbar.max = 100 * 24



        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                weatherHourlyContainer.smoothScrollToPosition(p1 / 100)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

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

        forecastWeather(api, index = 0)

        tomorrowButton.setOnClickListener {
            forecastWeather(api,1)
            tomorrowButton.setTextColor(resources.getColor(R.color.blue,null))
            todayButton.setTextColor(resources.getColor(R.color.blue_alpha30,null))

        }
        todayButton.setOnClickListener {
            forecastWeather(api,0)
            tomorrowButton.setTextColor(resources.getColor(R.color.blue_alpha30,null))
            todayButton.setTextColor(resources.getColor(R.color.blue,null))
        }
    }

    private fun currentWeather(api: WeatherApi) {
        Thread {


            val result = api.getWeather("Baku").execute().body()
            val temp = result?.currentWeather?.temp
            val humidity = result?.currentWeather?.humidity
            val wind = result?.currentWeather?.windVelocity
            val rainfall = result?.currentWeather?.rainfall
            val textTemp = result?.currentWeather?.weatherTextAndIcon?.text
            val imageTemp = result?.currentWeather?.weatherTextAndIcon?.icon
            val country = result?.location?.country
            val city = result?.location?.city
            val date = result?.location?.date

            //  Log.e("glide", imageTemp.toString())

            view?.post {
                humidityPercentage.text = humidity.toString() + " %"
                windVelocity.text = wind.toString() + " km/h"
                rainFall.text = rainfall.toString() + " cm"
                weatherTemperature.text = temp?.toFloat()?.roundToInt().toString()
                weatherText.text = textTemp.toString()
                location.text = country.toString() + "\n" + city.toString()
                dateWeather.text = getDateTime(date).toString()

                Glide
                    .with(this)
                    .load("https:$imageTemp")
                    .into(weatherImage);
            }

            //  Log.e("", imageTemp.toString())

            //  Log.e("rest", result.toString())

        }.start()
    }

    private fun forecastWeather(api: WeatherApi, index:Int) {
        Thread {
            val result = api.getForecast("Baku").execute().body()

            val hourForecast: List<WeatherHourlyItem> = result?.forecast?.forecastday?.get(index)?.hour
                ?.map { it ->
                    val timeHH = getHoursInHH(it.time)
                    val currentTime = getHoursInHH(System.currentTimeMillis()/1000)
                    WeatherHourlyItem(
                        it.image.icon,
                        it.temperature.toFloat()?.roundToInt().toString()+" °C",
                        getHour(it.time).toString(),
                        timeHH==currentTime && index == 0
                    )
                } ?: emptyList()

            view?.post {
                weatherHourlyAdapter.updateItems(
                    ArrayList(hourForecast)
                )
            }
        }.start()
    }

     fun getDateTime(s: Long?): String? {
        try {
            s ?: return ""
            val sdf = SimpleDateFormat("EEE, MMM d")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun getHour(s: Long?): String? {
        try {
            s ?: return ""
            val sdf = SimpleDateFormat("HH:mm")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
     fun getHoursInHH(s: Long?): String? {
        try {
            s ?: return ""
            val sdf = SimpleDateFormat("HH")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}
