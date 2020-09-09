package com.project.moon.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import com.project.moon.entity.SocketSingleton
import com.project.moon.entity.User
import com.project.moon.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private var socket = SocketSingleton.getSocket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            if (register_user.text.toString() == ""
                || register_pass.text.toString() == ""
                || register_name.text.toString() == ""
                || register_address.text.toString() == "") {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng thêm đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isEmailValid(register_user.text.toString())) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng nhập đúng địa chỉ email",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                DangKi()
            }
        }

        tv_back_signup.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun DangKi() {
        register_loading.visibility = View.VISIBLE
        var email: String = register_user.text.toString()
        var password: String= register_pass.text.toString()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var user = User(email, password, register_name.text.toString(), register_address.text.toString())
                    var mUser = mAuth.currentUser
                    mData = Firebase.database.reference
                    Log.d("id", mUser!!.uid)
                    mData.child("User").child(mUser.uid).setValue(user)
                    socket.emit("client-register-user", email)
                    register_loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        baseContext, "Successful registration!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    register_loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        baseContext, "Failed registration!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun isEmailValid(email: String?): Boolean {
        val pattern = Pattern.compile("^(.+)@(.+)$")
        val mat = pattern.matcher(email)
        return mat.matches()
    }
}