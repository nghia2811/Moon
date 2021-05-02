package com.project.moon.control.commands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.google.gson.Gson
import com.project.moon.R
import com.project.moon.entity.Commands
import com.project.moon.entity.SocketSingleton
import kotlinx.android.synthetic.main.fragment_commands.*
import kotlinx.android.synthetic.main.fragment_commands.view.*
import java.util.*

class CommandsFragment : Fragment() {

    private var socket = SocketSingleton.getSocket()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private lateinit var mDataCommand: DatabaseReference
    private var username: String? = null
    private var direction: Int? = 1
    var lstCommands: ArrayList<Commands> = ArrayList()
    var apiAdapter: ApiAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_commands, container, false)

        socket.connect()
        mAuth = Firebase.auth
        val user = mAuth.currentUser

        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                username = "${dataSnapshot.child("name").value.toString()}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        view.btn_ahead1.setOnClickListener {
            direction = 1;
            view.btn_ahead1.setBackgroundResource(R.drawable.ahead_click);
            view.btn_back1.setBackgroundResource(R.drawable.back);
            view.btn_right1.setBackgroundResource(R.drawable.right);
            view.btn_left1.setBackgroundResource(R.drawable.left);
        }

        view.btn_back1.setOnClickListener {
            direction = 2;
            view.btn_ahead1.setBackgroundResource(R.drawable.ahead);
            view.btn_back1.setBackgroundResource(R.drawable.back_click);
            view.btn_right1.setBackgroundResource(R.drawable.right);
            view.btn_left1.setBackgroundResource(R.drawable.left);
        }

        view.btn_right1.setOnClickListener {
            direction = 3;
            view.btn_ahead1.setBackgroundResource(R.drawable.ahead);
            view.btn_back1.setBackgroundResource(R.drawable.back);
            view.btn_right1.setBackgroundResource(R.drawable.right_click);
            view.btn_left1.setBackgroundResource(R.drawable.left);
        }

        view.btn_left1.setOnClickListener {
            direction = 4;
            view.btn_ahead1.setBackgroundResource(R.drawable.ahead);
            view.btn_back1.setBackgroundResource(R.drawable.back);
            view.btn_right1.setBackgroundResource(R.drawable.right);
            view.btn_left1.setBackgroundResource(R.drawable.left_click);
        }

        view.btn_send.setOnClickListener {
            val commands =
                Commands(direction!!, edt_khoangcach.text.toString().toInt(), username!!) // instance
            lstCommands.add(commands)
            val jsonString = Gson().toJson(commands)  // json string

            socket.emit("commands", jsonString)
        }

        apiAdapter = ApiAdapter(lstCommands)

        view.rv_api.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = apiAdapter
        }

        mDataCommand = Firebase.database.reference.child("commands")
        mDataCommand.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lstCommands = ArrayList<Commands>()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val p: Commands? = dataSnapshot1.getValue(Commands::class.java)
                    lstCommands.add(p!!)
                }

                apiAdapter = ApiAdapter(lstCommands)

                view.rv_api.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = apiAdapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}