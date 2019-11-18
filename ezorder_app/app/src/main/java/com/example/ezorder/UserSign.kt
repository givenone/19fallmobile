package com.example.ezorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.json.JSONObject


class UserSign : AppCompatActivity() {

    @ExperimentalStdlibApi
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
            params["username"] = id.text.toString()
            params["password"] = password.text.toString()
            params["email"] = email.text.toString()
            params["phone"] = phonenumber.text.toString()
            params["nickname"] = nickname.text.toString()
            params["isStore"] = "false"

            VolleyService.POSTVolley(this, "user/signup/", params) { testSuccess, response ->
                if (testSuccess) {
                    //Toast.makeText(this, response, Toast.LENGTH_LONG).show()

                    VolleyService.token = JSONObject(response).getString("token") // get token

                    val nextIntent = Intent(this@UserSign, MainUser::class.java)
                    nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(nextIntent)

                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }
            }


        }

    }
}