package com.karapetyan.weather.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.karapetyan.weather.R
import com.karapetyan.weather.data.worker.scheduleWorker
import com.karapetyan.weather.databinding.ActivityMainBinding
import com.karapetyan.weather.ui.pager.PagerFragment
import com.karapetyan.weather.utils.FragmentUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import kotlin.coroutines.CoroutineContext
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import com.karapetyan.weather.utils.InternetConnectionLiveData

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val mainViewModel: MainViewModel by viewModel()
    private var binding: ActivityMainBinding? = null
    private lateinit var internetConnectionLiveData: InternetConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.lifecycleOwner = this
        setTimeBasedBackground()

        internetConnectionLiveData = InternetConnectionLiveData(applicationContext)

        internetConnectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                binding?.tvInternet?.visibility = View.GONE
                mainViewModel.fetchWeatherData()
                scheduleWorker(this)
                openFragment()
            } else {
                closeFragment()
                binding?.tvInternet?.visibility = View.VISIBLE
            }
        }
    }

    fun hideSystemBars(activity: Activity) {
        val window = activity.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)

            val controller = window.insetsController
            controller?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
        }
    }

    @SuppressLint("ResourceType")
    private fun openFragment() {
        val fragmentManager = supportFragmentManager
        val pagerFragment = PagerFragment("")
        FragmentUtils().replaceFragment(
            fragmentManager,
            pagerFragment,
            "pagerFragment",
            true,
            R.id.main_container
        )
    }

    private fun closeFragment() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.backStackEntryCount > 0) {
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStack()
            }
        }
        for (fragment in fragmentManager.fragments) {
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    private fun setTimeBasedBackground() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val gradientColors = when (hour) {
            in 5..10 -> intArrayOf("#FFD194".toColorInt(), "#70E1F5".toColorInt()) // Morning
            in 11..16 -> intArrayOf("#87CEEB".toColorInt(), "#FFFFFF".toColorInt()) // Afternoon
            in 17..19 -> intArrayOf("#FFA17F".toColorInt(), "#00223E".toColorInt()) // Evening
            else -> intArrayOf("#141E30".toColorInt(), "#243B55".toColorInt()) // Night
        }

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            gradientColors
        )

        binding?.mainContainer?.background = gradient
    }
}
