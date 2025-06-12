package com.karapetyan.weather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import com.karapetyan.weather.R
import com.karapetyan.weather.databinding.SearchFragmentBinding
import com.karapetyan.weather.ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class SearchFragment : BaseFragment(), CoroutineScope {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: SearchFragmentBinding

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        bind()
        return binding.root
    }

    private fun bind() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchFragment = this@SearchFragment
        binding.viewModel = searchViewModel
        val searchField = binding.searchView.searchField

        searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.searchCity(searchField.text.toString())
                true
            } else
                false
        }
    }

    fun insertToDbAndClose() {
        launch {
            searchViewModel.insertSelectedCityToDb()
            goBack()
        }
    }

    fun goBack(){
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}
