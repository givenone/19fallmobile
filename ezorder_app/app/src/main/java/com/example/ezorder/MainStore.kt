package com.example.ezorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.R.layout
import androidx.annotation.NonNull
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.MenuItem
import androidx.navigation.Navigation


class MainStore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_store)

        val bottomNavigationView = findViewById(R.id.nav_store) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.manage_order -> {// TODO :: navigation in bottomnavigationView (Unsure)
                            Navigation.findNavController().navigate(R.id.action_profile_store_to_manage_orders)
                        }
                        R.id.manage_my_store -> {
                            Navigation.findNavController().navigate(R.id.action_manage_orders_to_profile_store)
                        }
                    }
                    return false
                }
            })

    }
}
