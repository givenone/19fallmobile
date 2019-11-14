import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.ezorder.VolleyService
import com.example.ezorder.manage_orders
import com.example.ezorder.whenorder
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.json.JSONArray
import org.json.JSONObject
import android.widget.ListView


class search : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(com.example.ezorder.R.layout.fragment_search, container, false)
        val listview = view.findViewById<ListView>(com.example.ezorder.R.id.listview) as ListView

        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "store/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {

                val jsonArr: JSONArray = JSONArray(response)

                val Adapter = SearchAdapter(getActivity()!!.getApplicationContext(), jsonArr)
                listview.adapter = Adapter

            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    companion object {
        fun newInstance(): search = search()
    }
}