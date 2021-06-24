package com.project.moon.view.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import com.project.moon.view.device.DeviceActivity
import com.project.moon.model.Device
import com.project.moon.view.settting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var doubleClick = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    var lstDevice: ArrayList<Device> = ArrayList()
    var deviceAdapter: DeviceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth
        val user = mAuth.currentUser

        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                Glide.with(this@MainActivity)
                    .load("${dataSnapshot.child("image").value.toString()}").into(img_avatar)
                tv_username.text = "${dataSnapshot.child("name").value.toString()}"
                tv_email.text = "${dataSnapshot.child("email").value.toString()}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        deviceAdapter = DeviceAdapter(this, lstDevice)

        rv_device.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = deviceAdapter
        }

        btn_setting.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            intent.putExtra("amount", lstDevice.size)
            startActivity(intent)
        }

        floatingActionButton_device.setOnClickListener {
            startActivity(Intent(this@MainActivity, DeviceActivity::class.java))
        }

        loadDataFromFirebase()
    }

    private fun loadDataFromFirebase() {
        var mUser = mAuth.currentUser
        mData = Firebase.database.reference.child("Device").child(mUser!!.uid)
        mData.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(Device::class.java)?.let {
                    lstDevice.add(it)
                }
                deviceAdapter!!.notifyDataSetChanged()
                device_loading.visibility = View.GONE
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                device_loading.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }
}