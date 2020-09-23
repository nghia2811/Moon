package com.project.moon.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import kotlinx.android.synthetic.main.fragment_device.*
import kotlinx.android.synthetic.main.fragment_device.view.*
import java.util.*

class DeviceFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    var lstDevice: ArrayList<String> = ArrayList()
    var deviceAdapter: DeviceAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_device, container, false)

        mAuth = Firebase.auth

        deviceAdapter = DeviceAdapter(this, lstDevice)

        view.rv_device.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = deviceAdapter
        }

        loadDataFromFirebase()
        view.device_loading.visibility = View.GONE
        return view
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

}