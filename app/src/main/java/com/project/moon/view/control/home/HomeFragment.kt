package com.project.moon.view.control.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.project.moon.R
import com.project.moon.model.SocketSingleton
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private var socket = SocketSingleton.getSocket()
    var url = "http://192.168.43.125/"
    var webview: WebView? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        webview = view.findViewById<View>(R.id.webView) as WebView
        webview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        webview!!.loadUrl(url!!)

        webview!!.settings.javaScriptEnabled = true

        socket.connect()

        view.btn_ahead.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    socket.emit("go-ahead", "go-ahead")
                }
                MotionEvent.ACTION_UP -> {
                    socket.emit("stop", "stop")
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        view.btn_back.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    socket.emit("go-back", "go-back")
                }
                MotionEvent.ACTION_UP -> {
                    socket.emit("stop", "stop")
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        view.btn_left.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    socket.emit("left", "left")
                }
                MotionEvent.ACTION_UP -> {
                    socket.emit("stop", "stop")
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        view.btn_right.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    socket.emit("right", "right")
                }
                MotionEvent.ACTION_UP -> {
                    socket.emit("stop", "stop")
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        return view
    }
}