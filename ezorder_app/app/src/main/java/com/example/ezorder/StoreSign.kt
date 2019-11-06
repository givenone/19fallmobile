package com.example.ezorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class StoreSign : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.store_activity_when_sign)

        val signupButton = findViewById<Button>(R.id.store_signup_button)

        signupButton.setOnClickListener {

            val id = findViewById<TextInputEditText>(R.id.store_signup_id)
            val password = findViewById<TextInputEditText>(R.id.store_signup_password)
            val email = findViewById<TextInputEditText>(R.id.store_signup_email)
            val phonenumber = findViewById<TextInputEditText>(R.id.store_signup_phonenumber)
            val storeinfo = findViewById<TextInputEditText>(R.id.store_signup_info)
            val storename = findViewById<TextInputEditText>(R.id.store_signup_storename)

            val params = HashMap<String, String>()
            params["username"] = id.text.toString()
            params["email"] = email.text.toString()
            params["password"] = password.text.toString()
            params["isStore"] = "true"
            params["phone"] = phonenumber.text.toString()
            params["name"] = storename.text.toString()
            params["information"] = storeinfo.text.toString()

            VolleyService.POSTVolley(this, params) { testSuccess, response ->
                if (testSuccess) {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()

                    VolleyService.token = JSONObject(response).getString("token") // get token

                    val nextIntent = Intent(this@StoreSign, MainStore::class.java)
                    nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(nextIntent)

                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
              }
            }


        }

    }
}