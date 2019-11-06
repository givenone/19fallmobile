package com.example.ezorder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_profile_store.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.json.JSONObject

class profile_store : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_profile_store, container, false)

        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                //Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()

                val jsonObj: JSONObject = JSONObject(response)

                val id = jsonObj.getString("id")
                val username = jsonObj.getString("username")
                val email = jsonObj.getString("email")
                val phone = jsonObj.getString("phone")
                val store_name = jsonObj.getString("name")
                val store_info = jsonObj.getString("information")

                view.findViewById<TextView>(R.id.store_name_text).text = "store name : " + jsonObj.getString("name")
                view.findViewById<TextView>(R.id.store_phone_number_text).text = "Contact : " + jsonObj.getString("phone")
                view.findViewById<TextView>(R.id.store_text).text = "Info : " + jsonObj.getString("information")

            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }

        view.edit_button.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.store_container, edit_my_store.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view

    }

    companion object {
        fun newInstance(): profile_store = profile_store()
    }
}
