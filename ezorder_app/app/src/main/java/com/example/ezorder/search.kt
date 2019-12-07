import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import org.json.JSONArray
import android.widget.ListView
import com.example.ezorder.*
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.location.LocationListener


class search : Fragment() {

    @ExperimentalStdlibApi
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

        val user_search_button = view.findViewById<ImageButton>(R.id.user_search_button)
        val lm = context!!.getSystemService(LOCATION_SERVICE) as LocationManager


        user_search_button.setOnClickListener {
            if ( ContextCompat.checkSelfPermission(getActivity()!!.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions( getActivity()!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0 )
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0.1f,locationListener)
            }

            try{
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0.1f,locationListener)
                val transaction = fragmentManager!!.beginTransaction()
                val nextIntent = Intent(getActivity()!!.getApplicationContext(), GMap::class.java)
                startActivity(nextIntent)
            }
            catch (ex : SecurityException) {
                Toast.makeText(getActivity()!!.getApplicationContext(),
                    ex.toString(), Toast.LENGTH_SHORT).show()
            }


        }
        return view
    }
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Toast.makeText(getActivity()!!.getApplicationContext(),
                "longitude : ${location.longitude} + langitude : ${location.latitude} ", Toast.LENGTH_SHORT).show()
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        fun newInstance(): search = search()
    }

    inner class MylocationListener: LocationListener {

        var mylocation : Location?

        constructor():super(){
            mylocation= Location("me")
            mylocation!!.longitude
            mylocation!!.latitude
        }

        override fun onLocationChanged(location: Location?) {
            mylocation = location
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

        override fun onProviderEnabled(p0: String?) {}

        override fun onProviderDisabled(p0: String?) {}
    }
}