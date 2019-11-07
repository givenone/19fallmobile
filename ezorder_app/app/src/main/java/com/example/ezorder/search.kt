import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.ezorder.R
import com.example.ezorder.VolleyService
import com.example.ezorder.manage_orders
import com.example.ezorder.whenorder
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.json.JSONArray
import org.json.JSONObject

class search : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_search, container, false)

        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()

                val jsonObj: JSONArray = JSONArray(response)


            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }

        view.user_order_button.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.user_container, whenorder.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    companion object {
        fun newInstance(): search = search()
    }
}