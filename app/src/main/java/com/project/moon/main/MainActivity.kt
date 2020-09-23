package com.project.moon.main

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var doubleClick = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private lateinit var pagerAdapter: MainPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth
        val user = mAuth.currentUser

        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                tv_username.text = "${dataSnapshot.child("name").value.toString()}"
                tv_email.text = "${dataSnapshot.child("email").value.toString()}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        pagerAdapter = MainPagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.vp_main)
        viewPager.adapter = pagerAdapter
        tablayout_main.setupWithViewPager(viewPager)
        setupTabIcons()
    }

    private fun setupTabIcons() {
        tablayout_main.getTabAt(0)?.setIcon(R.drawable.ic_baseline_settings_remote_24)
        tablayout_main.getTabAt(1)?.setIcon(R.drawable.ic_baseline_message_24)

        tablayout_main.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }
}