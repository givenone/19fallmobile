package com.example.ezorder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.ezorder.R
import com.example.ezorder.whenorder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray

class OrderAdapter (val context: Context, val menulist: JSONArray, val itemClick: (Int) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_whenorder, null)

        val menuName = view.findViewById<TextView>(R.id.listview_whenorder_menuname)
        val menuInfo = view.findViewById<TextView>(R.id.listview_whenorder_menudes)
        val menuOption= view.findViewById<TextView>(R.id.listview_whenorder_menu_options)
        val menuInputLayout=view.findViewById<TextInputEditText>(R.id.listview_whenorder_optionText)
        val menuImage=view.findViewById<ImageView>(R.id.listview_whenorder_menuimage)
        val menuButton=view.findViewById<Button>(R.id.listview_whenorder_button)
        val menu = menulist.getJSONObject(position)
        menuName.text = menu.getString("name")
        menuInfo.text = "price :"+ menu.getInt("information") + " Takeout available"+menu.getBoolean("take_out_available");
        menuOption.text=menu.getString("option")

        menuButton.setOnClickListener {

            itemClick(menu.getInt("id"))

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