package com.example.ezorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import search


class whenorder :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.whenorder, container, false)
    }

    companion object {
        fun newInstance(storeId: Int): whenorder {
            val fragment = whenorder()
            val args = Bundle()
            args.putInt("storeId", storeId)
            fragment.setArguments(args)
            return fragment
        }
    }
}
