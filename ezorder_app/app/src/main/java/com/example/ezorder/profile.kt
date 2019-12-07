import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.ezorder.R
import com.example.ezorder.VolleyService
import com.example.ezorder.edit_my_store
import com.example.ezorder.edit_my_user
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_store.view.*
import org.json.JSONObject


class profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)


        VolleyService.GETVolley(getActivity()!!.getApplicationContext(), "user/", VolleyService.token) { testSuccess, response ->
            if (testSuccess) {

                val jsonObj: JSONObject = JSONObject(response)

                view.findViewById<TextView>(R.id.user_username_text).text = "username : " + jsonObj.getString("username")
                view.findViewById<TextView>(R.id.user_email_text).text = "email : " + jsonObj.getString("email")
                view.findViewById<TextView>(R.id.user_nickname_text).text = "nickname : " + jsonObj.getString("nickname")
                view.findViewById<TextView>(R.id.user_phone_number_text).text = "phone number : " + jsonObj.getString("phone")

            } else {
                Toast.makeText(getActivity()!!.getApplicationContext(), response, Toast.LENGTH_LONG).show()
            }
        }

        view.user_profile_button.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.user_container, edit_my_user.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    companion object {
        fun newInstance(): profile = profile()
    }
}