package com.example.ezorder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_profile_store.view.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class edit_my_store : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_edit_my_store, container, false)

        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "user/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                //Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()

                val jsonObj: JSONObject = JSONObject(response)

                view.findViewById<TextView>(R.id.store_username_edit).text = jsonObj.getString("username")
                view.findViewById<TextView>(R.id.store_email_edit).text = jsonObj.getString("email")
                view.findViewById<TextView>(R.id.store_contact_info_edit).text = jsonObj.getString("phone")
                view.findViewById<TextView>(R.id.store_information_edit).text = jsonObj.getString("information")

            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }

        view.findViewById<Button>(R.id.store_edit_cfm_button).setOnClickListener {

            val params = HashMap<String, String>()
            params["username"] = view.findViewById<TextView>(R.id.store_name_edit).text.toString()
            params["phone"] = view.findViewById<TextView>(R.id.store_contact_info_edit).text.toString()
            params["information"] = view.findViewById<TextView>(R.id.store_information_edit).text.toString()

            VolleyService.PUTVolley(getActivity()!!.getApplicationContext(), "user/", params) { testSuccess, response ->
                if (testSuccess) {
                    Toast.makeText(getActivity()!!.getApplicationContext(), "edit success !", Toast.LENGTH_LONG).show()
                    fragmentManager!!.popBackStack() // return to back stack fragment
                } else {
                    Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }



    companion object {
        fun newInstance(): edit_my_store = edit_my_store()
    }
}
