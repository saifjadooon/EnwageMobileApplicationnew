package com.example.envagemobileapplication.Activities.Splash.landingScreens

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.envagemobileapplication.Activities.Login.LoginActivty
import com.example.envagemobileapplication.Adapters.LandingPagerAdapter
import com.example.envagemobileapplication.R
import com.example.envagemobileapplication.Utils.SharedPrefs


class LandingScreen : BaseActivity() {

    lateinit var viewPager: ViewPager
    lateinit var adapter: LandingPagerAdapter
    lateinit var iv_stage: ImageView
    lateinit var iv_skip: ImageView
    private val layoutResources = listOf(
        R.layout.landing_screen_one,
        R.layout.landing_screen_two,
        R.layout.landing_screen_three
    )
    private var currentLayoutIndex = 0
    private var currentLayout: View? = null
    private val delayMillis = 5000L // Delay between transitions in milliseconds
    private val handler = Handler()
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)

        initViews()
        listeners()
    }

    private fun listeners() {
        iv_stage.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < layoutResources.size - 1) {
                // If not on the last screen, go to the next screen
                viewPager.setCurrentItem(currentItem + 1, true)
            } else {
                // If on the last screen, start the next activity
                SharedPrefs.setFirstTimeLaunch(this, "isFirstTime", false)
                val intent = Intent(this, LoginActivty::class.java)
                finish()
                startActivity(intent)
            }
        }
        iv_skip.setOnClickListener {
            SharedPrefs.setFirstTimeLaunch(this, "isFirstTime", false)
            val intent = Intent(this, LoginActivty::class.java)
            startActivity(intent)

        }

        viewPager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    // Not needed for this case
                }

                override fun onPageSelected(position: Int) {
                    if (position == 0) {

                        iv_stage.setImageDrawable(resources.getDrawable(R.drawable.circle_state_one))


                    } else if (position == 1) {

                        iv_stage.setImageDrawable(resources.getDrawable(R.drawable.circle_state_wo))


                    } else if (position == 2) {

                        iv_stage.setImageDrawable(resources.getDrawable(R.drawable.ic_getstartrddd))
                    }

                }

                override fun onPageScrollStateChanged(state: Int) {
                    // Not needed for this case
                }
            })
    }

    private fun initViews() {
        iv_stage = findViewById(R.id.iv_stage)
        iv_skip = findViewById(R.id.iv_skip)
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        adapter = LandingPagerAdapter(this)
        viewPager.adapter = adapter
        currentLayout = findViewById(layoutResources[currentLayoutIndex])

        startAutomaticTransition()
    }

    private fun startAutomaticTransition() {
        handler.postDelayed({
            if (currentPage < 3 - 1) {
                currentPage++
                viewPager.setCurrentItem(currentPage, true)
                startAutomaticTransition() // Schedule the next transition
            }
        }, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Remove any pending callbacks to prevent memory leaks
    }

}
