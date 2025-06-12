package com.karapetyan.weather.ui.city

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.utils.GlideApp
import com.vaibhavlakhera.circularprogressview.CircularProgressView

class WeatherCityViewModel(private var repositoryRoom: WeatherRepositoryRoom) : ViewModel() {

    suspend fun getCity(cityName: String?): LiveData<WeatherData> {
        return repositoryRoom.getCity(cityName)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("progress")
        fun setProgress(view: CircularProgressView, progress: Int) {
            view.setProgress(progress, true)
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(view: ImageView, imageUrl: String?){
            val url = "http://openweathermap.org/img/wn/$imageUrl@2x.png"
            GlideApp.with(view.context)
                .load(url)
                .into(view)
        }
    }
}
