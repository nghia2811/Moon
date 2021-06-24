package com.project.moon.view.control

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.project.moon.R
import kotlinx.android.synthetic.main.activity_control.*

class ControlActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: ControlPagerAdapter
    private lateinit var viewPager: ViewPager
    var device: String? = null
    var image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        device = intent.getStringExtra("device")
        image = intent.getStringExtra("image")
        tv_devicename.text = device
        Glide.with(this@ControlActivity)
            .load(image).into(device_ct_img)

        pagerAdapter = ControlPagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.vp_control)
        viewPager.adapter = pagerAdapter
        tablayout.setupWithViewPager(viewPager)
    }
}