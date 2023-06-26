package com.blackjackgame.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blackjackgame.weatherapp.R

class WeatherDailyAdapter(private var items: ArrayList<WeatherDailyItem>) :
    RecyclerView.Adapter<WeatherDailyAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.day_image)
        val temperature: TextView = view.findViewById(R.id.day_temperature)
        val day: TextView = view.findViewById(R.id.day_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_daily, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.image.setImageResource(items[position].image)
        holder.temperature.text = items[position].temperature
        holder.day.text = items[position].day.toString().lowercase().replaceFirstChar { it.uppercase() }

    }

    override fun getItemCount() = 6
}