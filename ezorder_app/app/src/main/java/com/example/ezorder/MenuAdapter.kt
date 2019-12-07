package com.example.ezorder

import android.content.Context
import android.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray

class MenuAdapter (val context: Context, val storelist: JSONArray, val storeid: Int, val itemClick: (Int) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_edit_store_menu, null)

//       val menu_name = view.findViewById<TextView>(R.id.menu_name_text)
        //val price = view.findViewById<TextView>(R.id.price_text)
        val edit_button = view.findViewById<Button>(R.id.menu_edit_button)
        val delete_button = view.findViewById<Button>(R.id.menu_delete_button)

        val store = storelist.getJSONObject(position)
//        menu_name.text = store.getString("name")
        //price.text = store.getString("price")

        edit_button.setOnClickListener {
            //TODO :itemClick(store.getInt("id"))
        }
        delete_button.setOnClickListener {
            VolleyService.DELETEVolley(
                context,
                "store/{$storeid}/{${store.getString("id")}"
            ) { testSuccess, response ->
                if (testSuccess) {
                    itemClick(-1)
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                }
            }
        }
        return view

    }

    override fun getItem(position: Int): Any {
        return storelist.getJSONObject(position)

    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {

        return storelist.length()

    }

}

