package com.example.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
/*
        GlobalScope.launch {

            for(i in 1..2){
                delay(1000)

            }
        }
*/

        thread {
            Thread.sleep(1000)
            var myIntent = Intent(this, DisplayActivity::class.java)
            startActivity(myIntent)
        }

    }
}