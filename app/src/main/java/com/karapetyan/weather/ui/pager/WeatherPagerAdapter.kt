package com.karapetyan.weather.ui.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.ui.city.WeatherCityFragment

class WeatherPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var items: MutableList<WeatherData> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return WeatherCityFragment.newInstance(items[position])
    }

    override fun getCount(): Int {
        return items.size
    }

    fun setData(newData: MutableList<WeatherData>) {
        items = newData
        notifyDataSetChanged()
    }
}