package com.example.ezorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class MainStore : AppCompatActivity() {

    val mlistener = BottomNavigationView.OnNavigationItemSelectedListener { item : MenuItem ->
            when (item.getItemId()) {
                R.id.navigation_store_order -> {// TODO :: navigation in bottomnavigationView (Unsure)
                    val fragmentA = manage_orders.newInstance()
                    openFragment(fragmentA)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_store_profile -> {
                    val fragmentB = profile_store.newInstance()
                    openFragment(fragmentB)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_store_menu -> {
                    val fragmentC = edit_store_menu.newInstance()
                    openFragment(fragmentC)
                    return@OnNavigationItemSelectedListener true
                }
            }
        return@OnNavigationItemSelectedListener false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.store_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_store)

        val bottomNavigationView = findViewById(R.id.nav_store) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(mlistener)
        openFragment(profile_store.newInstance()) // default fragment : profile!
    }
}