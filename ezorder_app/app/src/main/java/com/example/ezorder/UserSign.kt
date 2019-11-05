package com.example.ezorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class UserSign : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_when_sign)

        val signupButton = findViewById<Button>(R.id.user_signup_button)

        signupButton.setOnClickListener {
            val nextIntent = Intent(this@UserSign, main_user::class.java)
            startActivity(nextIntent)
        }

    }
}