package com.project.moon.control

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.project.moon.R
import kotlinx.android.synthetic.main.activity_control.*

class ControlActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: ControlPagerAdapter
    private lateinit var viewPager: ViewPager
    var device: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        device = intent.getStringExtra("device")
        tv_devicename.text = device

        pagerAdapter = ControlPagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.vp_control)
        viewPager.adapter = pagerAdapter
        tablayout.setupWithViewPager(viewPager)
    }
}