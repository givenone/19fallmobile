import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.ezorder.*
import kotlinx.android.synthetic.main.activity_main_user.*
import kotlinx.android.synthetic.main.whenorder.view.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class order : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.fragment_order, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.store_menu_listview) as ListView
        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "order/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                val jsonArr: JSONArray = JSONArray(response)
                val Adapter = ListOrderAdapter(
                getActivity()!!.getApplicationContext(),
                jsonArr
            ) { order_id ->
                val frag_03 = detail_order()
                val bundle = Bundle()
                bundle.putInt("order_id",order_id)
                frag_03.arguments=bundle
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.user_container,frag_03)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            listview.adapter = Adapter
        }
        else {
            Toast.makeText(
                getActivity()!!.getApplicationContext(),
                "Test Fail ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    return view
}

companion object {
    fun newInstance(): order = order()
    }
}



