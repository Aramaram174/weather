package com.karapetyan.weather.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.karapetyan.weather.R
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.databinding.WeatherCityFragmentBinding
import com.karapetyan.weather.utils.DateUtils
import com.karapetyan.weather.utils.MathFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class WeatherCityFragment : Fragment(), CoroutineScope {

    private val weatherCityViewModel: WeatherCityViewModel by viewModel()
    private lateinit var binding: WeatherCityFragmentBinding
    private lateinit var weatherData: WeatherData

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    companion object {
        private const val ARG_WEATHER = "WeatherCityFragment"

        fun newInstance(weatherData: WeatherData): WeatherCityFragment {
            val args = Bundle()
            args.putSerializable(ARG_WEATHER, weatherData)
            val fragment = WeatherCityFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.weather_city_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.weatherCityFragment = this
        binding.mathFunctions = MathFunctions()
        binding.dateUtils = DateUtils()
        binding.viewModel = weatherCityViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherData = arguments?.getSerializable(ARG_WEATHER) as WeatherData
        launch {
            openCityPage(weatherData.name)
        }
    }

    private suspend fun openCityPage(cityName: String?) {
        weatherCityViewModel.getCity(cityName)
            .observe(viewLifecycleOwner, Observer { weatherData ->
                binding.weatherData = weatherData
            })
    }
}
