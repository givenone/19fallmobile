package com.example.ezorder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */

//when user click detail in order List
class detail_order : Fragment() {
    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //getting argument from order
        var arguid=-1
        if(arguments!!.getInt("order_id")!=null){
            arguid = arguments!!.getInt("order_id")
            Toast.makeText(
                getActivity()!!.getApplicationContext(),
                "Hey Guy {$arguid}",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            Toast.makeText(
                getActivity()!!.getApplicationContext(),
                "NO ARGUMENT",
                Toast.LENGTH_SHORT
            ).show()
        }
        // Inflate the layout for this fragment
        val order_id=arguid.toString()
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.fragment_detail_order, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.detail_order_listview) as ListView
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "order/$order_id/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                val textStoreName = view.findViewById<TextView>(R.id.detail_order_storename)
                val textWhenCreated = view.findViewById<TextView>(R.id.detail_order_created)
                val textTakeOut = view.findViewById<TextView>(R.id.detail_order_takeout)
                val textRequest = view.findViewById<TextView>(R.id.detail_order_request)
                val textEstimateTime = view.findViewById<TextView>(R.id.detail_order_estimatedTime)
                val textIsDone = view.findViewById<TextView>(R.id.detail_order_done)
                val json: JSONObject = JSONObject(response)
                val store_name = textStoreName.text
                val jsonArr: JSONArray = JSONObject(response).getJSONArray("menus")


                textStoreName.text = json.getString("store_name")



                //parsing created to show more prettier.
                var textCreated=json.getString("created")
                var delimeter="T"
                //parts[0] = date parts[1]= time
                val parts=textCreated.split(delimeter)
                delimeter=":"
                //times[0]=hour times[1]=min
                val times=parts[1].split(delimeter)
                textWhenCreated.text = parts[0]+"\t"+times[0]+":"+times[1]


                textTakeOut.text = "TakeOut: unImplemented TODO"
                textRequest.text = json.getString("request")
                textEstimateTime.text = "estimated Time: unImplemented TODO"
                if (json.getBoolean("done")) {
                    textIsDone.text = "Done"
                } else {
                    textIsDone.text = "UnDone"
                }

                val Adapter = detail_orderAdapter(
                    getActivity()!!.getApplicationContext(),
                    jsonArr
                )
                listview.adapter = Adapter
            }
            else {
                Toast.makeText(
                    getActivity()!!.getApplicationContext(),
                    "Test Fail {$order_id}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }
    companion object {
        fun newInstance(order_id: Int): detail_order {
            val fragment = detail_order()
            val args = Bundle()
            args.putInt("order_id", order_id)
            fragment.setArguments(args)
            return fragment
        }
    }
}

