import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.json.JSONArray
import android.widget.ListView
import com.example.ezorder.*


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

                val Adapter = SearchAdapter(
                    getActivity()!!.getApplicationContext(),
                    jsonArr
                ) { store_id ->

                    Toast.makeText(
                        getActivity()!!.getApplicationContext(),
                        " You Checked :" + " ${store_id}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val frag_02 = whenorder()
                    val bundle = Bundle()
                    bundle.putInt("store_id",store_id)
                    frag_02.arguments=bundle
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.user_container, frag_02)
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
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