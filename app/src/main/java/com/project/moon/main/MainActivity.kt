package com.project.moon.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import java.util.*

class MainActivity : AppCompatActivity() {

    private var doubleClick = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    var lstDevice: ArrayList<String> = ArrayList()
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

        loadDataFromFirebase()
        device_loading.visibility = View.GONE

    }

    private fun loadDataFromFirebase() {
//        val user = mAuth.currentUser
//        mData = Firebase.database.reference.child("Device").child(user!!.uid)
//        mData.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Setting values
//                lstUser.clear()
//                val p: User? = dataSnapshot.getValue(User::class.java)
//                username = p!!.name
//                for (element in p.group)
        lstDevice.add("Remote control arduino")
        deviceAdapter!!.notifyDataSetChanged()
//        device_loading.visibility = View.GONE
//            }

//            override fun onCancelled(databaseError: DatabaseError) {
//                device_loading.visibility = View.GONE
//                Toast.makeText(this@DeviceActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }
}