package com.blackjackgame.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.blackjackgame.weatherapp.R
import com.bumptech.glide.Glide

class WeatherHourlyAdapter(private var items: ArrayList<WeatherHourlyItem>) :
    RecyclerView.Adapter<WeatherHourlyAdapter.CardViewHolder>() {

    fun updateItems(list: ArrayList<WeatherHourlyItem>) {
        items = list
        notifyDataSetChanged()
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.current_weather)
        val temperature: TextView = view.findViewById(R.id.current_temperature)
        val time: TextView = view.findViewById(R.id.time)
        val background:ConstraintLayout = view.findViewById(R.id.icon_inactive)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_hourly, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        Glide
            .with(holder.itemView)
            .load("https:${items[position].image}")
            .into(holder.image)

        //  Log.e("glide",items[position].image)

        holder.temperature.text = items[position].temperature
        if (items[position].isNow) {
            holder.time.text = "now"
            holder.background.setBackgroundResource(R.drawable.background_icon_active)
        } else {
            holder.time.text = items[position].time
            holder.background.setBackgroundResource(R.drawable.background_icon_inactive)
        }

    }


    override fun getItemCount() = items.size
}