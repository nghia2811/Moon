package com.project.moon.controller.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.project.moon.controller.`interface`.ICommandController
import com.project.moon.model.Commands
import com.project.moon.model.SocketSingleton
import java.util.ArrayList

class CommandController : ICommandController  {

    private var socket = SocketSingleton.getSocket()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private lateinit var mDataCommand: DatabaseReference
    private var username: String? = null
    var lstCommands: ArrayList<Commands> = ArrayList()
    var lstID: ArrayList<String> = ArrayList()

    override fun getCommandList(): ArrayList<Commands> {
        mAuth = Firebase.auth
        val user = mAuth.currentUser

        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                username = "${dataSnapshot.child("name").value.toString()}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        mDataCommand = Firebase.database.reference.child("commands")
        mDataCommand.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lstCommands = ArrayList<Commands>()
                lstID = ArrayList<String>()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val p: Commands? = dataSnapshot1.getValue(Commands::class.java)
                    val id = dataSnapshot1.key
                    lstCommands.add(p!!)
                    lstID.add(id!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        return lstCommands
    }

    override fun sendCommand(commands: Commands) {
        socket.connect()
        val jsonString = Gson().toJson(commands)  // json string

        socket.emit("commands", jsonString)
    }
}