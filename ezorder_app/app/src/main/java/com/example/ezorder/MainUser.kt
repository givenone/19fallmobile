package com.example.ezorder

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment



abstract class main_user : AppCompatActivity() {

    private val mlistener = BottomNavigationView.OnNavigationItemSelectedListener { item : MenuItem ->
        when (item.itemId) {
            R.id.navigation_search -> {
                val fragmentA = search.newInstance()
                openFragment(fragmentA)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_order -> {
                val fragmentB = order.newInstance()
                openFragment(fragmentB)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val fragmentC = profile.newInstance()
                openFragment(fragmentC)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.user_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_user)
        bottomNavigationView.setOnNavigationItemSelectedListener(mlistener);
        openFragment(profile.newInstance()) // default fragment : profile !
    }
}
