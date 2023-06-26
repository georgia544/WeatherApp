package com.blackjackgame.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blackjackgame.weatherapp.R

class WeatherHourlyAdapter(private var items: ArrayList<WeatherHourlyItem>) :
    RecyclerView.Adapter<WeatherHourlyAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.current_weather)
        val temperature: TextView = view.findViewById(R.id.current_temperature)
        val time: TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_hourly, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.image.setImageResource(items[position].image)
        holder.temperature.text = items[position].temperature
        holder.time.text = items[position].time

    }

    override fun getItemCount() = items.size
}