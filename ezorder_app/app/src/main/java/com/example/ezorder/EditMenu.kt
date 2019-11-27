package com.example.ezorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONArray
import org.json.JSONObject

class EditMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_edit_store_menu, container, false)

        val storeid = arguments!!.getInt("storeID")
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/{$storeid}/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                val jsonArr: JSONArray = JSONObject(response).getJSONArray("menus")
                val Adapter = MenuAdapter(getActivity()!!.getApplicationContext(), jsonArr, storeid) { store_id ->
                    if (store_id == -1)
                    {
                        //TODO : Reload Required (Deleted)
                    }
                    else if (store_id > 0)
                    {
                        // TODO : Go to Edit Page
                    }

                }

                listview.adapter = Adapter
            }
            else {
                Toast.makeText(
                    getActivity()!!.getApplicationContext(), response, Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<ImageButton>(R.id.add_menu_btn).setOnClickListener {
            //Todo : Add Menu Page
        }

        return view
    }


    companion object {
        fun newInstance(storeId: Int): EditMenu {
            val fragment = EditMenu()
            val args = Bundle()
            args.putInt("storeId", storeId)
            fragment.setArguments(args)
            return fragment
        }
    }
}
