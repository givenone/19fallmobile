package com.example.ezorder

import android.content.Context
import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ezorder.R
import com.example.ezorder.whenorder
import org.json.JSONArray
//Adapter for Order
class ListOrderAdapter (val context: Context, val orderList: JSONArray, val itemClick: (Int) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */


        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_order_user, null)
        //finding the layout to change color if done
        val layOut:LinearLayout=view.findViewById(R.id.listview_order_user_layout)

        val storeName = view.findViewById<TextView>(R.id.listview_order_user_storeName)
        val created = view.findViewById<TextView>(R.id.listview_order_user_created)
        val expectedTime = view.findViewById<TextView>(R.id.listview_order_user_expectedTime)
        val button=view.findViewById<Button>(R.id.listview_order_user_button)
        val order = orderList.getJSONObject(position)

        storeName.text = order.getString("store_name")

        //parsing created to show more prettier.
        var textCreated=order.getString("created")
        var delimeter="T"
        //parts[0] = date parts[1]= time
        val parts=textCreated.split(delimeter)
        delimeter=":"
        //times[0]=hour times[1]=min
        val times=parts[1].split(delimeter)
        created.text = parts[0]+"\t"+times[0]+":"+times[1]
        //TODO implement Expected Time have to talk with JaeWon Ahn
        expectedTime.text="Expected Time : not yet implemented"

        //If order is done turn color to gray
        if(order.getBoolean("done")){
            layOut.setBackgroundColor(Color.DKGRAY)
            button.setBackgroundColor(Color.GRAY)
        }
        button.setOnClickListener {
            itemClick(order.getInt("id"))
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return orderList.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return orderList.length()
    }

}