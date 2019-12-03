package com.example.ezorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.ezorder.VolleyService.POSTVolley
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN  = 1998

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{

                val account = task.getResult(ApiException::class.java)
                val cred = GoogleAuthProvider.getCredential(account!!.idToken,null)

                Toast.makeText(this, " You Checked :" + " ${account!!.idToken}"
                        , Toast.LENGTH_SHORT).show()

            }
            catch (e: Exception){
                Log.d("junwon", e.toString())
           }
        }
    }
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page_app)

        val loginButton = findViewById<Button>(R.id.login_button)
        val signupButton = findViewById<Button>(R.id.signup_button)

        //google login starts

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val google = findViewById<SignInButton>(R.id.sign_in_button)
        google.setOnClickListener{
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

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

            VolleyService.POSTVolley(this, "user/login/", params) { testSuccess, response ->
                if (testSuccess) {

                    VolleyService.token = JSONObject(response).getString("token") // get token

                    if(!JSONObject(response).getBoolean("isStore"))
                    {// if user
                        val nextIntent = Intent(this@MainActivity, MainUser::class.java)
                        nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(nextIntent)
                    }
//                    else
//                    {// if store
//                        val nextIntent = Intent(this@MainActivity, MainStore::class.java)
//                        nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
//                        startActivity(nextIntent)
//                    }
                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    //// TODO 화면전환 및 백엔드와의 request 통신 결과 띄우기
}
