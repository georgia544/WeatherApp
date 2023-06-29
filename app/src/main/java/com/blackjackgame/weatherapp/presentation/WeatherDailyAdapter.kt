package com.blackjackgame.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blackjackgame.weatherapp.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date

class WeatherDailyAdapter(private var items: ArrayList<WeatherDailyItem>) :
    RecyclerView.Adapter<WeatherDailyAdapter.CardViewHolder>() {

    fun updateItems(list: ArrayList<WeatherDailyItem>) {
        items = list
        notifyDataSetChanged()
    }


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
        Glide
            .with(holder.itemView)
            .load("https:${items[position].image}")
            .into(holder.image)
        holder.temperature.text = items[position].temperature
        holder.day.text = getDayOfTheWeek(items[position].day)


    }

    override fun getItemCount() = items.size

    fun getDayOfTheWeek(s: Long?): String? {
        try {
            s ?: return ""
            val sdf = SimpleDateFormat("EEEE")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}