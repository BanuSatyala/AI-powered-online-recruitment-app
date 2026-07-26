package com.example.aionlinerecurtement.Users

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.aionlinerecurtement.R

class WebView : AppCompatActivity() {

    lateinit var web:WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
         web=findViewById(R.id.web)
        web.loadUrl(Uri.parse(intent.getStringExtra("uri")).toString())
        web.settings.javaScriptEnabled=true
        web.settings.setSupportZoom(true)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    web.goBack()
    }
}