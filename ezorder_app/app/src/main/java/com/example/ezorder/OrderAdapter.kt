package com.example.ezorder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ezorder.R
import com.example.ezorder.whenorder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

//whenorder Adapter. return string and parsing at whenorder.kt
class OrderAdapter (val context: Context, val menulist: JSONArray, val itemClick: (String) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_whenorder, null)

        val menuName = view.findViewById<TextView>(R.id.listview_whenorder_menuname)
        val menuInfo = view.findViewById<TextView>(R.id.listview_whenorder_menudes)
        val menuTake = view.findViewById<TextView>(R.id.listview_whenorder_takeout)
        val menuAmmount=view.findViewById<TextView>(R.id.listview_whenorder_amount)
        val menuExpectedTime = view.findViewById<TextView>(R.id.listview_whenorder_expectedTime)
        val menuImage=view.findViewById<ImageView>(R.id.listview_whenorder_menuimage)
        val menuButton=view.findViewById<Button>(R.id.listview_whenorder_button)
        val menuButton2=view.findViewById<Button>(R.id.listview_whenorder_button2)
        val menu = menulist.getJSONObject(position)


        try{
            val url = menu.getString("image")
            if(url != null) Picasso.get().load(url).into(view.findViewById<ImageView>(R.id.listview_whenorder_menuimage))
        }catch(e:Exception){}

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
        //TODO I have to implement option
        menuAmmount.text="0"

        //when user click add
        menuButton.setOnClickListener {
            itemClick("add"+"\t"+menu.getInt("id").toString()+"\t"+menu.getString("name")+"\t"+menu.getInt("price").toString()+"\t"+position.toString())
        }

        //when user click delete
        menuButton2.setOnClickListener{
            if(menuAmmount.text!="0"){
                menuAmmount.text=(menuAmmount.text.toString().toInt()-1).toString()
            }
            else{
                menuAmmount.text="0"
            }
            itemClick("delete"+"\t"+menu.getInt("id").toString()+"\t"+menu.getString("name")+"\t"+menu.getInt("price").toString()+"\t"+position.toString())
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