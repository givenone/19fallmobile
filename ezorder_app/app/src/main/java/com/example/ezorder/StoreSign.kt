package com.example.ezorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StoreSign : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.store_activity_when_sign)

        val signupButton = findViewById<Button>(R.id.store_signup_button)

        signupButton.setOnClickListener {
            val nextIntent = Intent(this@StoreSign, MainStore::class.java)
            startActivity(nextIntent)
        }

    }
}