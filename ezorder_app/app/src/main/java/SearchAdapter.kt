import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.ezorder.R
import com.example.ezorder.whenorder
import org.json.JSONArray

class SearchAdapter (val context: Context, val storelist: JSONArray) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_search, null)

        val storeName = view.findViewById<TextView>(R.id.listview_search_storename)
        val storeInfo = view.findViewById<TextView>(R.id.listview_search_information)
        val button = view.findViewById<Button>(R.id.listview_search_button)

        val store = storelist.getJSONObject(position)
        storeName.text = store.getString("name")
        storeInfo.text = store.getString("information")

        button.setOnClickListener {

            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.user_container, whenorder.newInstance(store.getInt("id")))
            transaction.addToBackStack(null)
            transaction.commit()

        }
        return view
    }

    override fun getItem(position: Int): Any {
        return storelist.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return storelist.length()
    }

}