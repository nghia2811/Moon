package com.project.moon.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import com.project.moon.model.SocketSingleton
import com.project.moon.view.main.MainActivity
import com.project.moon.view.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        mAuth = Firebase.auth
        if (mAuth.currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            if (login_user.text.toString() == "" || login_pass.text.toString() == "")
                Toast.makeText(this, "Vui lòng thêm đủ thông tin!!", Toast.LENGTH_SHORT).show()
            else {
                DangNhap()
            }
        }

        tv_register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun DangNhap() {
        login_loading.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(login_user.text.toString(), login_pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        baseContext, "Authentication successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    login_loading.visibility = View.INVISIBLE
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                    login_loading.visibility = View.INVISIBLE
                }
            }
    }

}