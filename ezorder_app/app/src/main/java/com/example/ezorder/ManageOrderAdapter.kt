package com.example.ezorder

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.json.JSONArray

class ManageOrderAdapter(val context: Context, val orderlist: JSONArray, val itemClick: (Int, Int) -> Unit) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

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

        val order = orderlist.getJSONObject(position) // order json object
        //{
        //    "id": 6,
        //    "request": "안재원은 잘생겼다",
        //    "done": false,
        //    "created": "2019-11-25T15:03:20.414353",
        //    "user_nickname": "hahaho",
        //    "menus": [
        //        {
        //            "id": 10,
        //            "menu_name": "Cheese Stick",
        //            "quantity": 3,
        //            "option": [
        //                {
        //                    "text": "hot or cold?",
        //                    "choice": "yes"
        //                },
        //                {
        //                    "text": "Add Shot",
        //                    "choice": "yes"
        //                }
        //            ]
        //        },
        //        {
        //            "id": 11,
        //            "menu_name": "Cheese Stick",
        //            "quantity": 2,
        //            "option": [
        //                {
        //                    "text": "hot or cold?",
        //                    "choice": "no"
        //                },
        //                {
        //                    "text": "Add Shot",
        //                    "choice": "no"
        //                }
        //            ]
        //        }
        //    ],
        //    "total_price": 3000000
        //}
        request.text = order.getString("request")
        userName.text = order.getString("user_nickname")
        menuPrice.text = order.getString("total_price")

        var textCreated=order.getString("created")
        var delimeter="T"
        val parts=textCreated.split(delimeter)
        delimeter=":"
        val times=parts[1].split(delimeter)
        created.text = parts[0]+"\t"+times[0]+":"+times[1]


        val layOut: LinearLayout =view.findViewById(R.id.listview_order_store_layout)
        if(order.getBoolean("done")){
            layOut.setBackgroundColor(Color.DKGRAY)
            confirmButton.setBackgroundColor(Color.GRAY)
            DetailButton.setBackgroundColor(Color.GRAY)
        }

        confirmButton.setOnClickListener {
            itemClick(0, order.getInt("id"))
            // PUT REQUEST TO baseurl/order/order_id/

            // TODO :: make notification
        }
        DetailButton.setOnClickListener {
            itemClick(1, order.getInt("id"))
        }
        val menu = order.getJSONArray("menus")

        var menutext = ""
        for(i in 0 until menu.length())
        {
            val t = menu.getJSONObject(i)
            menutext += "${t.getString("menu_name")} : ${t.getString("quantity")} \n ${t.getString("option")}\n"
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