package com.example.ezorder

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.whenorder.*
import kotlinx.android.synthetic.main.whenorder.view.*
import org.json.JSONArray
import org.json.JSONObject
import search
import java.util.*
import kotlin.collections.HashMap


@ExperimentalStdlibApi
class whenorder :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var arguid=-1
        if(arguments!!.getInt("store_id")!=null){
            arguid = arguments!!.getInt("store_id")
        }
        // Inflate the layout for this fragment
        val storeid=arguid.toString()
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.whenorder, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.listview_whenorder) as ListView
        var orderArray = IntArray(100)
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/$storeid/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                val textTotalPrice=view.findViewById<TextView>(R.id.whenorder_totalPrice)
                view.orderButton.setOnClickListener { view ->
                    val params = JSONObject()
                    params.put("request"," ")
                    params.put("total_price",whenorder_totalPrice.text.toString())
                    val jsonArray = JSONArray()
                    for(i in 0..99){
                        if(orderArray[i]!=0){
                            val obj = JSONObject()
                            obj.put("id",i.toString())
                            obj.put("quantity",orderArray[i].toString())
                            obj.put("options","[]")
                            jsonArray.put(obj)
                        }
                    }
                    Log.d("JSON ARRAY LOOKS LIKE THIS",jsonArray.toString())
                    params.put("menus",jsonArray)
                    VolleyService.DATA_POSTVolley(getActivity()!!.getApplicationContext(), "order/" ,params) { testSuccess, response ->
                        if (testSuccess) {
                            Toast.makeText(getActivity()!!.getApplicationContext(), "order success !", Toast.LENGTH_LONG).show()
                            val transaction = fragmentManager!!.beginTransaction()
                            transaction.replace(R.id.user_container, order.newInstance())
                            transaction.addToBackStack(null)
                            transaction.commit()//move to order
                        } else {
                            Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                var totalPrice =0
                val jsonArr: JSONArray = JSONObject(response).getJSONArray("menus")
                val length = jsonArr.length()
                val Adapter = OrderAdapter(
                    getActivity()!!.getApplicationContext(),
                    jsonArr
                ) { menu_id ->
                    var delimeter="\t"
                    //parts[0] = id parts[1]=name parts[2]=price
                    val parts=menu_id.split(delimeter)
                    val addOrDelete=parts[0]
                    val id=parts[1].toInt()
                    val name=parts[2]
                    val price=parts[3].toInt()
//                    if someone click Add button twice the item has to be canceled.
//                    if orderArray[id]=1 then item[i] is in the orderArray List.
                    if(addOrDelete.equals("add",false)){
                        orderArray[id]=orderArray[id]+1
                        totalPrice=totalPrice+price
                        textTotalPrice.text=totalPrice.toString()
                    }
                    else{
                        if(orderArray[id]!=0){
                            orderArray[id]=orderArray[id]-1
                            totalPrice=totalPrice-price
                            textTotalPrice.text=totalPrice.toString()
                        }
                    }
                }
                listview.adapter = Adapter
            }
            else {
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
