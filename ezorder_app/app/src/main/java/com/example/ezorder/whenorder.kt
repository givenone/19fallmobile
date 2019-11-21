package com.example.ezorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.whenorder.view.*
import org.json.JSONArray
import org.json.JSONObject
import search


class whenorder :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var arguid=-1
        if(arguments!!.getInt("store_id")!=null){
            arguid = arguments!!.getInt("store_id")
            Toast.makeText(
                getActivity()!!.getApplicationContext(),
                "Hey Guy {$arguid}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Inflate the layout for this fragment
        val storeid=arguid.toString()
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.whenorder, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.listview_whenorder) as ListView
        var orderArray = IntArray(100)
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/$storeid/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                Toast.makeText(
                    getActivity()!!.getApplicationContext(),
                    "Test Success {$storeid}",
                    Toast.LENGTH_SHORT
                ).show()
                //when Order button clicked.
                view.orderButton.setOnClickListener { view ->
                    Toast.makeText(
                        getActivity()!!.getApplicationContext(),
                        "Order is clicked!",
                        Toast.LENGTH_SHORT
                    ).show()
                    //transition to order
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.user_container,order.newInstance())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                var totalPrice =0
                var orderList=""
                val jsonArr: JSONArray = JSONObject(response).getJSONArray("menus")
                val length = jsonArr.length()
                Toast.makeText(
                    getActivity()!!.getApplicationContext(),
                    "Json Array's length is {$length}",
                    Toast.LENGTH_SHORT
                ).show()
                val Adapter = OrderAdapter(
                    getActivity()!!.getApplicationContext(),
                    jsonArr
                ) { menu_id ->
                    var delimeter="\t"
                    //parts[0] = id parts[1]=name parts[2]=price
                    val parts=menu_id.split(delimeter)
                    val id=parts[0].toInt()
                    val name=parts[1]
                    val price=parts[2].toInt()
                    val textOrderList=view.findViewById<TextView>(R.id.whenorder_orderList)
                    val textTotalPrice=view.findViewById<TextView>(R.id.whenorder_totalPrice)
//                    if someone click Add button twice the item has to be canceled.
//                    if orderArray[id]=1 then item[i] is in the orderArray List.
                    if(orderArray[id]==1){
                        orderArray[id]=0
                        totalPrice=totalPrice-price
                        var newParts=orderList.split(name)
                        orderList=""
                        for(item in newParts ){
                            orderList=orderList+" "+item
                        }
                        textOrderList.text=orderList
                        textTotalPrice.text=totalPrice.toString()
                    }
                    else {
                        orderArray[id] = 1
                        orderList=orderList+" "+name
                        totalPrice=totalPrice+price
                        textOrderList.text=orderList
                        textTotalPrice.text=totalPrice.toString()
                    }
                    Toast.makeText(
                        getActivity()!!.getApplicationContext(),
                        " You Checked :" + " ${menu_id}",
                        Toast.LENGTH_SHORT
                    ).show()

//
//                    val transaction = fragmentManager!!.beginTransaction()
//                    transaction.replace(R.id.user_container,order.newInstance())
//                    transaction.addToBackStack(null)
//                    transaction.commit()
//
                }
                listview.adapter = Adapter
//
            } else {
                Toast.makeText(
                    getActivity()!!.getApplicationContext(),
                    "Test Fail {$storeid}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    companion object {
        fun newInstance(storeId: Int): whenorder {
            val fragment = whenorder()
            val args = Bundle()
            args.putInt("storeId", storeId)
            fragment.setArguments(args)
            return fragment
        }
    }
}
