package com.project.moon.device

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.project.moon.entity.Device
import kotlinx.android.synthetic.main.activity_device.*

class DeviceActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private lateinit var storage: FirebaseStorage
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        btn_add_device.setOnClickListener {
            if (edt_device_name.text.toString() == ""
                || edt_dv_description.text.toString() == ""
                || uri == null
            ) {
                Toast.makeText(
                    this@DeviceActivity,
                    "Vui lòng thêm đầy đủ thông tin!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                addDevice()
            }
        }

        add_device_img.setOnClickListener {
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

    private fun addDevice() {
        dv_loading.visibility = View.VISIBLE
        var devicename: String = edt_device_name.text.toString()
        var description: String = edt_dv_description.text.toString()

        val storageRef: StorageReference =
            storage.getReferenceFromUrl("gs://project-3-1ca3b.appspot.com")

        val mountainsRef = storageRef.child("device").child("$devicename.png")

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
                var newDevice = Device(devicename, description, url)
                var mUser = mAuth.currentUser
                mData = Firebase.database.reference
                mData.child("Device").child(mUser!!.uid).child(devicename)
                    .setValue(newDevice)
                dv_loading.visibility = View.INVISIBLE
                Toast.makeText(
                    baseContext, "Successful!",
                    Toast.LENGTH_SHORT
                ).show()
                // Sign in success, update UI with the signed-in user's information
                edt_device_name.text = null
                edt_dv_description.text = null

            } else {
                // Handle failures
                dv_loading.visibility = View.INVISIBLE
                Toast.makeText(
                    baseContext, "Failed!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
            add_device.setImageURI(data?.data)
            uri = data?.data
        }
    }

}