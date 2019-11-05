package com.example.ezorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page_app)

        val loginButton = findViewById<Button>(R.id.login_button)
        val signupButton = findViewById<Button>(R.id.signup_button)

        signupButton.setOnClickListener{
            val nextIntent = Intent(this, SelectSU::class.java)
            startActivity(nextIntent)
        }

        loginButton.setOnClickListener{}
    }
    //// TODO 화면전환 및 백엔드와의 request 통신 결과 띄우기
}
