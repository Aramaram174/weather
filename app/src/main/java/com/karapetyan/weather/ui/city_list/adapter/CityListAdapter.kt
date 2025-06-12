package com.karapetyan.weather.ui.city_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.databinding.CityListAdapterItemBinding
import com.karapetyan.weather.utils.DateUtils
import com.karapetyan.weather.utils.MathFunctions

class CityListAdapter(private val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<CityListAdapter.CityListViewHolder>() {

    private var items: MutableList<WeatherData> = mutableListOf()

    fun setData(newData: MutableList<WeatherData>) {
        items = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityListAdapterItemBinding.inflate(inflater)
        return CityListViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    fun getNameCityPosition(position: Int): String {
        return items[position].name
    }

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) =
        holder.bind(items[position], clickListener)

    class CityListViewHolder(val binding: CityListAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherData, clickListener: (String) -> Unit) {
            binding.weatherData = item
            binding.executePendingBindings()
            binding.mathFunctions = MathFunctions()
            binding.dateUtils = DateUtils()
            itemView.setOnClickListener { clickListener(item.name)}
        }
    }
}