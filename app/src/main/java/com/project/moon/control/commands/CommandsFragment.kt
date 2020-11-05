package com.project.moon.control.commands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.project.moon.R
import com.project.moon.entity.Commands
import com.project.moon.entity.SocketSingleton
import kotlinx.android.synthetic.main.fragment_commands.*
import kotlinx.android.synthetic.main.fragment_commands.view.*

class CommandsFragment : Fragment() {

    private var socket = SocketSingleton.getSocket()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_commands, container, false)

        socket.connect()

        view.btn_send.setOnClickListener {
            val commands =
                Commands(edt_huongdi.text.toString(), edt_khoangcach.text.toString()) // instance
            val jsonString = Gson().toJson(commands)  // json string

            socket.emit("commands", jsonString)
        }

        return view
    }
}