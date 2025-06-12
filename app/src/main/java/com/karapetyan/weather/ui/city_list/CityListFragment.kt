package com.karapetyan.weather.ui.city_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karapetyan.weather.R
import com.karapetyan.weather.databinding.CityListFragmentBinding
import com.karapetyan.weather.ui.BaseFragment
import com.karapetyan.weather.ui.city_list.adapter.CityListAdapter
import com.karapetyan.weather.ui.city_list.adapter.SwipeToDeleteCallback
import com.karapetyan.weather.ui.pager.PagerFragment
import com.karapetyan.weather.ui.search.SearchFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class CityListFragment : BaseFragment(), CoroutineScope {

    private val cityListViewModel: CityListViewModel by viewModel()
    private lateinit var binding: CityListFragmentBinding

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private lateinit var adapter: CityListAdapter
    private var cityListRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.city_list_fragment, container, false)
        binding.cityListFragment = this
        binding.viewModel = cityListViewModel
        binding.lifecycleOwner = this

        cityListRecyclerView = binding.cityListRv
        cityListRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        adapter = CityListAdapter { cityName -> cityItemClicked(cityName) }
        cityListRecyclerView!!.adapter = adapter

        launch {
            cityListViewModel.getAllCities()
                .observe(viewLifecycleOwner, Observer { weatherDataList ->
                    adapter.setData(weatherDataList)
                })
        }

        enableSwipeToDelete()

        return binding.root
    }

    @SuppressLint("ResourceType")
    fun openSearchPage() {
        replaceFragment(SearchFragment())
    }

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    launch {
                        cityListViewModel.deleteCity(adapter.getNameCityPosition(viewHolder.adapterPosition))
                    }
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(cityListRecyclerView)
    }

    private fun cityItemClicked(cityName: String) {
        replaceFragment(PagerFragment(cityName))
    }
}
