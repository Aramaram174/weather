package com.karapetyan.weather.utils

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FragmentUtils {

    @SuppressLint("ResourceType")
    fun addFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        fragmentTag: String?,
        addToBackStack: Boolean,
        @LayoutRes containerResId: Int
    ) {
        var fragmentTag = fragmentTag
        if (fragmentTag == null) fragmentTag = fragment.javaClass.simpleName
        val backStackName = fragment.javaClass.simpleName
        val transaction: FragmentTransaction =
            fragmentManager.beginTransaction().add(containerResId, fragment, fragmentTag)
        if (addToBackStack) {
            transaction.addToBackStack(backStackName)
        }
        transaction.commit()
    }

    fun removeFraagment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.popBackStack()
        fragmentManager.beginTransaction().remove(fragment).commit()
    }

    @SuppressLint("ResourceType")
    fun replaceFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        fragmentTag: String?,
        addToBackStack: Boolean,
        @LayoutRes containerResId: Int
    ) {
        var fragmentTag = fragmentTag
        if (fragmentTag == null) fragmentTag = fragment.javaClass.simpleName
        val backStackName = fragment.javaClass.simpleName
        val transaction: FragmentTransaction =
            fragmentManager.beginTransaction().replace(containerResId, fragment, fragmentTag)
        if (addToBackStack) {
            transaction.addToBackStack(backStackName)
        }
        transaction.commit()
    }
}