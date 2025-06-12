package com.karapetyan.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.karapetyan.weather.R
import com.karapetyan.weather.utils.FragmentUtils

open class BaseFragment : Fragment() {

    private lateinit var myContext: FragmentActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            myContext = context
        } else {
            throw IllegalStateException("Attached context is not a FragmentActivity")
        }
    }

    @SuppressLint("ResourceType")
    fun addFragment(fragment: Fragment) {
        val fragmentManager = myContext.supportFragmentManager
        FragmentUtils().addFragment(fragmentManager, fragment, fragment.javaClass.name, true, R.id.main_container)
    }

    @SuppressLint("ResourceType")
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = myContext.supportFragmentManager
        FragmentUtils().replaceFragment(fragmentManager, fragment, fragment.javaClass.name, true, R.id.main_container)
    }

    fun removeFragment(fragment: Fragment){
        val fragmentManager = myContext.supportFragmentManager
        FragmentUtils().removeFraagment(fragmentManager, fragment)
    }
}