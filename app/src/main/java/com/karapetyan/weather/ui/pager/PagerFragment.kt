package com.karapetyan.weather.ui.pager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.karapetyan.weather.R
import com.karapetyan.weather.databinding.PagerFragmentBinding
import com.karapetyan.weather.ui.BaseFragment
import com.karapetyan.weather.ui.city_list.CityListFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class PagerFragment(private var cityName: String = "") : BaseFragment(), CoroutineScope {

    private val pagerViewModel: PagerViewModel by viewModel()
    private lateinit var binding: PagerFragmentBinding
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private lateinit var myContext: FragmentActivity
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: WeatherPagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            myContext = context
        } else {
            throw IllegalStateException("Attached context is not a FragmentActivity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.pager_fragment, container, false)
        binding.pagerFragment = this
        binding.viewModel = pagerViewModel
        binding.lifecycleOwner = this

        viewPager = binding.weathersVp
        pagerAdapter = WeatherPagerAdapter(myContext.supportFragmentManager)
        viewPager.adapter = pagerAdapter
        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager, true)

        launch {
            pagerViewModel.getAllCities()
                .observe(viewLifecycleOwner, Observer { weatherDataList ->
                    pagerAdapter.setData(weatherDataList)

                    if (cityName.isNotEmpty()) {
                        val position = weatherDataList.indexOfFirst { it.name == cityName }
                        viewPager.currentItem = if (position != -1) position else 0
                    }
                })
        }
        return binding.root
    }

    @SuppressLint("ResourceType")
    fun openCityListPage() {
        replaceFragment(CityListFragment())
    }
}
