package com.example.notify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        imageView=findViewById(R.id.splash_image)
        textView=findViewById(R.id.text)

        val animation:Animation=AnimationUtils.loadAnimation(applicationContext,R.anim.anim2)
        imageView.startAnimation(animation)
        textView.startAnimation(animation)

        Handler().postDelayed({
            val mainIntent = Intent(this@MainActivity, Home::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)





    }
}