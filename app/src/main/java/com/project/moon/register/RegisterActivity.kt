package com.project.moon.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.moon.R
import com.project.moon.entity.User
import com.project.moon.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private lateinit var storage: FirebaseStorage
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        btn_register.setOnClickListener {
            if (register_user.text.toString() == ""
                || register_pass.text.toString() == ""
                || register_name.text.toString() == ""
                || uri == null
            ) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng thêm đầy đủ thông tin!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isEmailValid(register_user.text.toString())) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng nhập đúng địa chỉ email!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (register_pass.text.toString() != register_confirm_pass.text.toString()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng xác nhận lại mật khẩu!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                DangKi()
            }
        }

        tv_back_signup.setOnClickListener {
            super.onBackPressed()
        }

        iv_logo_register.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }

    private fun DangKi() {
        register_loading.visibility = View.VISIBLE
        var email: String = register_user.text.toString()
        var password: String = register_pass.text.toString()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val storageRef: StorageReference =
                        storage.getReferenceFromUrl("gs://project-3-1ca3b.appspot.com")

                    val mountainsRef = storageRef.child("avatar").child(email + "_avatar" + ".png")

                    val uploadTask = mountainsRef.putFile(uri!!)

                    val urlTask = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        mountainsRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var url = task.result.toString()
                            var user = User(email, password, register_name.text.toString(), url)
                            var mUser = mAuth.currentUser
                            mData = Firebase.database.reference
                            mData.child("User").child(mUser!!.uid).setValue(user)
                            register_loading.visibility = View.INVISIBLE
                            Toast.makeText(
                                baseContext, "Successful registration!",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Sign in success, update UI with the signed-in user's information
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            // Handle failures
                            // ...
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    register_loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        baseContext, "Failed registration!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("error", task.exception.toString())
                }
            }
    }

    fun isEmailValid(email: String?): Boolean {
        val pattern = Pattern.compile("^(.+)@(.+)$")
        val mat = pattern.matcher(email)
        return mat.matches()
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            set_avatar.setImageURI(data?.data)
            uri = data?.data
        }
    }

}