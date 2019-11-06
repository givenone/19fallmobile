package com.example.ezorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.ezorder.VolleyService.POSTVolley
import com.google.android.material.textfield.TextInputEditText

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

        loginButton.setOnClickListener{
            val email = findViewById<TextInputEditText>(R.id.login_email)
            val password = findViewById<TextInputEditText>(R.id.login_password)

            val params = HashMap<String, String>()
            params["username"] = email.text.toString()
            params["password"] = password.text.toString()
            VolleyService.POSTVolley(this, "login/", params) { testSuccess, response ->
                if (testSuccess) {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                    // TODO :: GET TOKEN !
                    if(true)
                    {// if user
                        val nextIntent = Intent(this@MainActivity, main_user::class.java)
                        nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(nextIntent)

                    }
                    else
                    {// if store
                        val nextIntent = Intent(this@MainActivity, MainStore::class.java)
                        nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(nextIntent)
                    }
                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    //// TODO 화면전환 및 백엔드와의 request 통신 결과 띄우기
}
