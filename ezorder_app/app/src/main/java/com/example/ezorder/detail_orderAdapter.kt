package com.example.ezorder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ezorder.R
import com.example.ezorder.whenorder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray

class detail_orderAdapter (val context: Context, val menulist: JSONArray) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_detail_order, null)

        val menuName = view.findViewById<TextView>(R.id.listview_detail_order_menuname)
        val menuQuantity = view.findViewById<TextView>(R.id.listview_detail_order_quantity)
        val menuOption=view.findViewById<TextView>(R.id.listview_storemenu_options)
        val menu = menulist.getJSONObject(position)
        menuName.text = menu.getString("menu_name")
        menuQuantity.text="Amount: "+menu.getInt("quantity").toString()
        var optionString:String=""
        val optionArray=menu.getJSONArray("option")
        for(i in 0 until optionArray.length()){
            val item = optionArray.getJSONObject(i)
            optionString=optionString+item.getString("text")+" -> "+item.getString("choice")+"\n"
        }
        menuOption.text=optionString

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