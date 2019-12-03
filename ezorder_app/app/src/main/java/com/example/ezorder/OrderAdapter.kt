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

class OrderAdapter (val context: Context, val menulist: JSONArray, val itemClick: (String) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_whenorder, null)

        val menuName = view.findViewById<TextView>(R.id.listview_whenorder_menuname)
        val menuInfo = view.findViewById<TextView>(R.id.listview_whenorder_menudes)
        val menuTake = view.findViewById<TextView>(R.id.listview_whenorder_takeout)
        val menuAmmount=view.findViewById<TextView>(R.id.listview_whenorder_amount)
        val menuExpectedTime = view.findViewById<TextView>(R.id.listview_whenorder_expectedTime)
//        val menuOption= view.findViewById<TextView>(R.id.listview_whenorder_menu_options)
//        val menuInputLayout=view.findViewById<TextInputEditText>(R.id.listview_whenorder_optionselect)
        val menuImage=view.findViewById<ImageView>(R.id.listview_whenorder_menuimage)
        val menuButton=view.findViewById<Button>(R.id.listview_whenorder_button)
        val menuButton2=view.findViewById<Button>(R.id.listview_whenorder_button2)
        val menu = menulist.getJSONObject(position)
        menuName.text = menu.getString("name")
        menuExpectedTime.text="Expected Time: "+menu.getString("expected_time")
        if(menu.getBoolean("take_out_available")){
            menuInfo.text = "price :"+ menu.getInt("price")
            menuTake.text= " Take out is not available "
        }
        else{
            menuInfo.text = "price :"+ menu.getInt("price")
            menuTake.text = " Take out is available "
        }
//        menuOption.text=menu.getString("option")
        menuAmmount.text="0"
        menuButton.setOnClickListener {
            menuAmmount.text=(menuAmmount.text.toString().toInt()+1).toString()
            itemClick("add"+"\t"+menu.getInt("id").toString()+"\t"+menu.getString("name")+"\t"+menu.getInt("price").toString())
        }
        menuButton2.setOnClickListener{
            if(menuAmmount.text!="0"){
                menuAmmount.text=(menuAmmount.text.toString().toInt()-1).toString()
            }
            else{
                menuAmmount.text="0"
            }
            itemClick("delete"+"\t"+menu.getInt("id").toString()+"\t"+menu.getString("name")+"\t"+menu.getInt("price").toString())
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