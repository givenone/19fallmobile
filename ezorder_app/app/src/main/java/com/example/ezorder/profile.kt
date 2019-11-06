import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ezorder.R
import com.example.ezorder.VolleyService
import org.json.JSONObject


class profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "user/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()

                val jsonObj: JSONObject = JSONObject(response)

                val id = jsonObj.getString("id")
                val username = jsonObj.getString("username")
                val email = jsonObj.getString("email")
                val nickname = jsonObj.getString("nickname")
                val phone = jsonObj.getString("phone")

                // TODO : Set the Values !


            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        fun newInstance(): profile = profile()
    }
}