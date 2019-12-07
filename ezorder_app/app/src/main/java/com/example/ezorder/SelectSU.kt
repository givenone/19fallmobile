package com.example.ezorder

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_select_su.*

class SelectSU : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_select_su)

        // Get radio group selected item using on checked change listener
        SU.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                //Toast.makeText(applicationContext," You Checked :"+
                  //      " ${radio.text}",
                   // Toast.LENGTH_SHORT).show()
            })


        SU_submit_button.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = SU.checkedRadioButtonId

            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                if(id == R.id.SU_store)
                {
                    val nextIntent = Intent(this@SelectSU, StoreSign::class.java)
                    nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(nextIntent)
                }
                else if(id == R.id.SU_user)
                {
                    val nextIntent = Intent(this@SelectSU, UserSign::class.java)
                    nextIntent.setFlags(nextIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(nextIntent)
                }
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click :" +
                        " nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
}