package com.example.ezorder

import android.content.Context
import android.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray

class MenuAdapter (val context: Context, val menulist: JSONArray, val storeid: Int, val itemClick: (Int) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_edit_store_menu, null)

        val menu_name = view.findViewById<TextView>(R.id.listview_storemenu_menuname)
        val price = view.findViewById<TextView>(R.id.listview_storemenu_price)
        val take_out = view.findViewById<TextView>(R.id.listview_storemenu_takeout)
        val expected_time = view.findViewById<TextView>(R.id.listview_storemenu_expectedTime)
        val option = view.findViewById<TextView>(R.id.listview_storemenu_options)

        val edit_button = view.findViewById<Button>(R.id.menu_edit_button)
        val delete_button = view.findViewById<Button>(R.id.menu_delete_button)

        val menu = menulist.getJSONObject(position)
        var option_text = ""
        menu_name.text = menu.getString("name")
        price.text = menu.getString("price")
        take_out.text = menu.getString("take_out_available")
        expected_time.text = menu.getString("expected_time")
        val op = menu.getJSONArray("option")

        for(i in 0 until menu.length())
        {
            val t = op.getJSONObject(i)
            option_text += "{<<${t.getString("text")}>>\n${t.getString("choice")}\n"
        }
        option.text = option_text

        edit_button.setOnClickListener {
            //TODO :itemClick(store.getInt("id")) :: will use wookje's work.
        }
        delete_button.setOnClickListener {
            VolleyService.DELETEVolley(
                context, "store/{$storeid}/{${menu.getString("id")}") { testSuccess, response ->
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
        return menulist.getJSONObject(position)

    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {

        return menulist.length()

    }

}

