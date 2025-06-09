package com.example.emma_test_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // --- declaración de componentes ---
    //deeplink
    private lateinit var deeplinkTxtStatus: TextView
    private lateinit var deeplinkTxtLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //deeplink
        deeplinkTxtStatus = findViewById(R.id.txt_dch_deeplink)
        deeplinkTxtLink = findViewById(R.id.txt_deeplink)

    }

    override fun onResume() {
        super.onResume()

        // deeplink


    }
}