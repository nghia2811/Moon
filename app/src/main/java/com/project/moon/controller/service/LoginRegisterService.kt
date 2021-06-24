package com.project.moon.controller.service

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.project.moon.controller.`interface`.IDeviceController
import com.project.moon.controller.`interface`.ILoginRegisterService
import com.project.moon.model.SocketSingleton
import com.project.moon.model.User
import com.project.moon.view.login.LoginActivity
import com.project.moon.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginRegisterService : ILoginRegisterService {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference

    override fun login(username: String, password: String): Boolean {
        var success = true
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener() { task ->
                success = task.isSuccessful
            }
        return success
    }

    override fun register(user: User): Boolean {
        var success = true

        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    success = true
                    var mUser = mAuth.currentUser
                    mData = Firebase.database.reference
                    mData.child("User").child(mUser!!.uid).setValue(user)
                    }

                 else {
                    success = false
                    Log.d("error", task.exception.toString())
                }
            }
        return success
    }
}