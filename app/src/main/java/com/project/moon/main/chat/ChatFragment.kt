package com.project.moon.main.chat

import android.content.Intent
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
import com.project.moon.R
import com.project.moon.entity.Message
import com.project.moon.entity.SocketSingleton
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_chat.view.*
import org.json.JSONException
import org.json.JSONObject

class ChatFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var socket = SocketSingleton.getSocket()
    var lstMessage: ArrayList<Message> = ArrayList()
    var messageAdapter: MessageAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)

//        socket.connect()
//        socket.on("server-send-message", onListMessage)

        mAuth = Firebase.auth
        val user = mAuth.currentUser

        messageAdapter = MessageAdapter(lstMessage)

        view.rv_message.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            // set the custom adapter to the RecyclerView
            adapter = messageAdapter
        }

        view.btn_send.setOnClickListener {
            if (!view.edt_message.text.toString().isEmpty()) {
                val obj = JSONObject()
                try {
                    obj.put("user", user!!.email.toString())
                    obj.put("message", view.edt_message.text.toString())
//                    socket.emit("client-send-message", obj)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                view.edt_message.setText(null)
            } else
                Toast.makeText(this.activity, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private val onListMessage = Emitter.Listener { args ->
        try {
            var message = args[0] as JSONObject
            this.activity?.runOnUiThread {
                lstMessage.add(Message(message.getString("user"), message.getString("message")))
                messageAdapter?.notifyItemInserted(lstMessage.size - 1)
                view?.rv_message?.smoothScrollToPosition(lstMessage.size - 1)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}