package com.example.envagemobileapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import com.example.envagemobileapplication.R

class LandingPagerAdapter(private val context: Context) : PagerAdapter() {

    private val layoutIds = arrayOf(
        R.layout.landing_screen_one,
        R.layout.landing_screen_two,
        R.layout.landing_screen_three
    )

    override fun getCount(): Int {
        return layoutIds.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val layout = layoutInflater.inflate(layoutIds[position], container, false)
        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)

    }
}