package com.example.ezorder


import android.app.PendingIntent.getActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import order
import org.json.JSONArray

/**
 * A simple [Fragment] subclass.
 */
class manage_orders : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.fragment_manage_orders, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.manage_orders_listview) as ListView
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "order/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                val jsonArr: JSONArray = JSONArray(response)
                val Adapter = ManageOrderAdapter(getActivity()!!.getApplicationContext(), jsonArr
            ) { flag, order_id ->
                    if(flag == 0) // confirm order
                    {
                        VolleyService.PUTVolley(getActivity()!!.getApplicationContext(), "order/{$order_id}/", null){testSuccess, response ->
                            if(testSuccess){
                                //reload fragment
                                fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                            }
                            else{
                                Toast.makeText(
                                    getActivity()!!.getApplicationContext(), response, Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else if(flag == 1) // view detail of the order
                    {// follow what wookje did to go into detail_order
                        val frag_03 = detail_order()
                        val bundle = Bundle()
                        bundle.putInt("order_id",order_id)
                        frag_03.arguments=bundle
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.user_container,frag_03)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }

            }
            listview.adapter = Adapter
        }
        else {
            Toast.makeText(
                getActivity()!!.getApplicationContext(), response, Toast.LENGTH_SHORT
            ).show()
        }
    }

    return view
}

companion object {
    fun newInstance(): order = order()
    }
}
