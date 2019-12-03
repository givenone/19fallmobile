package com.example.ezorder

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.json.JSONArray

class ManageOrderAdapter(val context: Context, val orderlist: JSONArray, val itemClick: (String) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_manage_orders, null)

        val menuName = view.findViewById<TextView>(R.id.listview_order_store_menuName)
        val userName = view.findViewById<TextView>(R.id.listview_order_store_nickname)
        val created = view.findViewById<TextView>(R.id.listview_order_store_created)
        val request = view.findViewById<TextView>(R.id.listview_order_store_request)
        val menuTake = view.findViewById<TextView>(R.id.listview_order_store_takeout)
        val menuPrice=view.findViewById<TextView>(R.id.listview_order_store_price)
        val menuExpectedTime = view.findViewById<TextView>(R.id.listview_order_store_expectedTime)
//        val menuOption= view.findViewById<TextView>(R.id.listview_whenorder_menu_options)
//        val menuInputLayout=view.findViewById<TextInputEditText>(R.id.listview_whenorder_optionselect)
        val confirmButton=view.findViewById<Button>(R.id.listview_manage_orders_confirm)
        val DetailButton=view.findViewById<Button>(R.id.listview_manage_orders_detail)

        val order = orderlist.getJSONObject(position)
        request.text = order.getString("request")
        userName.text = order.getString("user_nickname")
        menuPrice.text = order.getString("total_price")
        var textCreated=order.getString("created")
        var delimeter="T"
        //parts[0] = date parts[1]= time
        val parts=textCreated.split(delimeter)
        delimeter=":"
        //times[0]=hour times[1]=min
        val times=parts[1].split(delimeter)
        created.text = parts[0]+"\t"+times[0]+":"+times[1]


        val layOut: LinearLayout =view.findViewById(R.id.listview_order_store_layout)
        if(order.getBoolean("done")){
            layOut.setBackgroundColor(Color.DKGRAY)
            confirmButton.setBackgroundColor(Color.GRAY)
            DetailButton.setBackgroundColor(Color.GRAY)
        }

        confirmButton.setOnClickListener {
            //itemClick(order.getInt("id"))
        }
        DetailButton.setOnClickListener {
            //itemClick(order.getInt("id"))
        }
        val menu = order.getJSONArray("menus")

        var menutext = ""
        for(i in 0 until menu.length())
        {
            val t = menu.getJSONObject(i)
            menutext += "{${t.getString("menu_name")} : ${t.getString("quantity")} \n ${t.getJSONArray("option")}\n"
        }
        menuName.text = menutext
        //TODO:: menuExpectedTime.text="Expected Time: "+menu.getString("expected_time")


        return view
    }

    override fun getItem(position: Int): Any {
        return orderlist.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return orderlist.length()
    }

}