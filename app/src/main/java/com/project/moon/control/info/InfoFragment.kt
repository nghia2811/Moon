package com.project.moon.control.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import com.project.moon.entity.SocketSingleton
import com.project.moon.login.LoginActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_info.view.*
import org.json.JSONException
import org.json.JSONObject

class InfoFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    var arrayAdapter: ArrayAdapter<String>? = null
    var userList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_info, container, false)

//        view.info_loading.visibility = View.VISIBLE

        mAuth = Firebase.auth
        val currentUser = mAuth.currentUser

        arrayAdapter = this.activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, userList) }
        view.rv_user.adapter = arrayAdapter

        view.btn_logout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this.activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.activity?.finish()
        }
        return view
    }

}