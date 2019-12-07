package com.example.ezorder

import android.app.DownloadManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.popup_whenorder.*
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
        val requestTextView=view.findViewById<TextView>(R.id.whenorder_request)
        //orderArray[i] is ammount of menu id i
        var orderArray = IntArray(100)
        var requestOrder = " "
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/$storeid/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                val textTotalPrice=view.findViewById<TextView>(R.id.whenorder_totalPrice)
                //when user click order button
                view.orderButton.setOnClickListener { view ->
                    val params = JSONObject()
                    params.put("request",requestOrder)
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
                    //post to order database
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
                //order Adapter
                val Adapter = OrderAdapter(
                    getActivity()!!.getApplicationContext(),
                    jsonArr
                ) { menu_id ->
                    var delimeter = "\t"
                    //parts[0] = id parts[1]=name parts[2]=price
                    val parts = menu_id.split(delimeter)
                    val addOrDelete = parts[0]
                    val id = parts[1].toInt()
                    val name = parts[2]
                    val price = parts[3].toInt()
                    val position=parts[4].toInt()
                    //when user click add
                    if (addOrDelete.equals("add", false)) {

                        val popupView : View = getLayoutInflater().inflate(R.layout.popup_whenorder, null)
                        val mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        val menuname=popupView.findViewById<TextView>(R.id.popup_whenorder_menuName)
                        val request=popupView.findViewById<TextInputEditText>(R.id.popup_whenorder_request)
                        val addButton=popupView.findViewById<Button>(R.id.popup_add)
                        menuname.text=name
                        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정

                        mPopupWindow.setFocusable(true)
                        // 외부 영역 선택히 PopUp 종료

                        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
                        addButton.setOnClickListener { popupView->
                            requestOrder=requestOrder+"\n"+name+" : "+request.text.toString()
                            orderArray[id] = orderArray[id] + 1
                            val listChildView=listview.getChildAt(position)
                            val ammountTextView:TextView=listChildView.findViewById(R.id.listview_whenorder_amount)
                            ammountTextView.text=(ammountTextView.text.toString().toInt()+1).toString()

                            totalPrice = totalPrice + price
                            textTotalPrice.text = "Total Price : "+totalPrice.toString()
                            requestTextView.text=requestOrder
                            mPopupWindow.dismiss()
                        }
//                        Button cancel = (Button) popupView.findViewById(R.id.Cancel);
//                        cancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mPopupWindow.dismiss();
//                            }
//                        })

//                        Button ok = (Button) popupView.findViewById(R.id.Ok);
//                        ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT)
//                                    .show();
//                            }
//                        }
                    }
                    //when user click delete
                    else {
                        if (orderArray[id] != 0) {
                            orderArray[id] = orderArray[id] - 1
                            totalPrice = totalPrice - price
                            textTotalPrice.text = totalPrice.toString()
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

