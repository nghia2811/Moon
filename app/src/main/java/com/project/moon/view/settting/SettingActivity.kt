package com.project.moon.view.settting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import com.project.moon.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        mAuth = Firebase.auth

        val user = mAuth.currentUser
        st_amount.text = intent.getIntExtra("amount", 0).toString()

        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                Glide.with(this@SettingActivity)
                    .load("${dataSnapshot.child("image").value.toString()}").into(setting_image)
                st_username.text = "${dataSnapshot.child("name").value.toString()}"
                st_email.text = "${dataSnapshot.child("email").value.toString()}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SettingActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        btn_logout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this@SettingActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this@SettingActivity.finish()
        }
    }
}