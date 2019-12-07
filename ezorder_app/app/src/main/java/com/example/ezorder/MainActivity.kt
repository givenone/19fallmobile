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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId


class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN  = 1998

    @ExperimentalStdlibApi
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


                val email = account.email
                val name = account.familyName + account.givenName
                val nickname = account.displayName

                Toast.makeText(this, " You Checked :" + " ${account!!.idToken} + $email + $name"
                    , Toast.LENGTH_SHORT).show()
                val params = HashMap<String, String>()
                params["username"] = email.toString()
                params["password"] = nickname.toString()
                // Firebase ID
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@OnCompleteListener
                        }

                        // Get new Instance ID token
                        val token = task.result?.token
                        params.put("token", token!!)
                        // Log and toast
                        //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                    })

                VolleyService.POSTVolley(this, "user/login/", params) { testSuccess, response ->
                    if (testSuccess) {

                        VolleyService.token = JSONObject(response).getString("token") // get token

                        if(!JSONObject(response).getBoolean("isStore"))
                        {// if user
                            val nextIntent = Intent(this@MainActivity, MainUser::class.java)
                            //nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(nextIntent)
                        }
                        else
                        {// if store
                            val nextIntent = Intent(this@MainActivity, MainStore::class.java)
                            //nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(nextIntent)
                        }
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                    }
                }

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
            // Firebase ID
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    params.put("token", token!!)
                    // Log and toast
                    //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })

            VolleyService.POSTVolley(this, "user/login/", params) { testSuccess, response ->
                if (testSuccess) {

                    VolleyService.token = JSONObject(response).getString("token") // get token

                    if(!JSONObject(response).getBoolean("isStore"))
                    {// if user
                        val nextIntent = Intent(this@MainActivity, MainUser::class.java)
                        //nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(nextIntent)
                    }
                    else
                    {// if store
                        val nextIntent = Intent(this@MainActivity, MainStore::class.java)
                        //nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(nextIntent)
                    }
                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
