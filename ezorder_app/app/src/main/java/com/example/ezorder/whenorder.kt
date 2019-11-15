package com.example.ezorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import org.json.JSONArray
import org.json.JSONObject
import search


class whenorder :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val id = arguments!!.getString("storeId")
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.whenorder, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.listview_whenorder) as ListView
        var orderArray = IntArray(100)
        var i=0;
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/{$id}/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {

                val jsonArr: JSONArray = JSONObject(response).getJSONArray("menus")

                val Adapter = OrderAdapter(
                    getActivity()!!.getApplicationContext(),
                    jsonArr
                ) { menu_id ->
                    orderArray[i]=menu_id
                    i=i+1
                    Toast.makeText(
                        getActivity()!!.getApplicationContext(),
                        " You Checked :" + " ${menu_id}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.user_container,order.newInstance())
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
                listview.adapter = Adapter

            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }

        return view
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
