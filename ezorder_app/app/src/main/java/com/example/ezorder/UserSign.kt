package com.example.ezorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor.getFlags
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class UserSign : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_when_sign)

        val signupButton = findViewById<Button>(R.id.user_signup_button)

        signupButton.setOnClickListener {

            val id = findViewById<TextInputEditText>(R.id.user_signup_id)
            val password = findViewById<TextInputEditText>(R.id.user_signup_password)
            val email = findViewById<TextInputEditText>(R.id.user_signup_email)
            val phonenumber = findViewById<TextInputEditText>(R.id.user_signup_phonenumber)
            val nickname = findViewById<TextInputEditText>(R.id.user_signup_nickname)

            val params = HashMap<String, String>()
            params[""] = id.text.toString()
            params[""] = password.text.toString()
            params[""] = email.text.toString()
            params[""] = phonenumber.text.toString()
            params[""] = nickname.text.toString()


            VolleyService.POSTVolley(this, params) { testSuccess, response ->
                if (testSuccess) {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                    //TODO :: token get needed
                    val nextIntent = Intent(this@UserSign, main_user::class.java)
                    nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(nextIntent)

                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }
            }


        }

    }
}