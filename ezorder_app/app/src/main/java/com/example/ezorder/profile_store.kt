package com.example.ezorder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile_store.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class profile_store : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_profile_store, container, false)

        view.edit_button.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.store_container, manage_my_store.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view

    }

    companion object {
        fun newInstance(): profile_store = profile_store()
    }
}
