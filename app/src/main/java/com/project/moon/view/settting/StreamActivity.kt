package com.project.moon.view.settting

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.project.moon.R


class StreamActivity : AppCompatActivity() {
    var url = "https://qldt.hust.edu.vn/"
    var webview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream)

        webview = findViewById<View>(R.id.webView) as WebView
        webview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        webview!!.loadUrl(url!!)

        webview!!.settings.javaScriptEnabled = true
//        webview!!.loadData(html, "text/html", null)

    }
}