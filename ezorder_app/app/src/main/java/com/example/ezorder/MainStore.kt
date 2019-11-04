package com.example.ezorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil


class MainStore : AppCompatActivity() {
    //idk how to do the binding anymore...
    //private lateinit var binding: ActivityMainStoreBinding

    //current random value of the store name
    //should match store name in data base when actually implemented
    //private val storeName: StoreName = StoreName("Cass Town")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main_store)
        setContentView(R.layout.activity_main_store)

        //binding.storeName = storeName


    }
}
