package com.example.ezorder

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.FragmentActivity

import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONArray
import profile

//참고 : https://mailmail.tistory.com/17
class GMap : FragmentActivity(), OnMapReadyCallback {

    //구글맵참조변수
    var mMap: GoogleMap? = null
    var long = 0.0
    var lat = 0.0
    //마커정보창 클릭리스너는 다작동하나, 마커클릭리스너는 snippet정보가 있으면 중복되어 이벤트처리가 안되는거같다.
    // oneMarker(); 는 동작하지않으나 manyMarker(); 는 snippet정보가 없어 동작이가능하다.

    //정보창 클릭 리스너
    var infoWindowClickListener: GoogleMap.OnInfoWindowClickListener =
        GoogleMap.OnInfoWindowClickListener { marker ->
            val markerId = marker.id
            //Toast.makeText(this@GMap, "정보창 클릭 Marker ID : $markerId", Toast.LENGTH_SHORT)
              //  .show()
        }

    //마커 클릭 리스너
    var markerClickListener: GoogleMap.OnMarkerClickListener =
        GoogleMap.OnMarkerClickListener { marker ->
            val markerId = marker.id
            //선택한 타겟위치
            val location = marker.position
            Toast.makeText(
                this@GMap,
                "Info : " + marker.title + "\n" + marker.snippet,
                Toast.LENGTH_SHORT
            ).show()
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.googlemap)

        // SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this) //getMapAsync must be called on the main thread.
    }

    override//구글맵을 띄울준비가 됬으면 자동호출된다.
    fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //지도타입 - 일반
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL

        val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED )
            {
                ActivityCompat.requestPermissions( this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0 )
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0.1f,locationListener)
            }

        try
        {
            ActivityCompat.requestPermissions( this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0 )
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0.1f,locationListener)
        }
        catch (ex : SecurityException) {
                Toast.makeText(this.getApplicationContext(),
                    ex.toString(), Toast.LENGTH_SHORT).show()
        }


        // manyMarker();
    }

    //마커하나찍는 기본 예제
    fun oneMarker(long : Double, lat : Double) {
        // 서울 여의도에 대한 위치 설정
        val seoul = LatLng(long, lat)

        // 구글 맵에 표시할 마커에 대한 옵션 설정  (알파는 좌표의 투명도이다.)
        val makerOptions = MarkerOptions()
        makerOptions
            .position(seoul)
            .title("Store")
            .snippet("sample store,,,")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            .alpha(0.5f)

        // 마커를 생성한다. showInfoWindow를 쓰면 처음부터 마커에 상세정보가 뜨게한다. (안쓰면 마커눌러야뜸)
        mMap!!.addMarker(makerOptions) //.showInfoWindow();

        //정보창 클릭 리스너
        mMap!!.setOnInfoWindowClickListener(infoWindowClickListener)

        //마커 클릭 리스너
        mMap!!.setOnMarkerClickListener(markerClickListener)

        //카메라를 여의도 위치로 옮긴다.
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        //처음 줌 레벨 설정 (해당좌표=>서울, 줌레벨(16)을 매개변수로 넣으면 된다.) (위에 코드대신 사용가능)(중첩되면 이걸 우선시하는듯)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16f))

        mMap!!.setOnMarkerClickListener {
            Toast.makeText(this@GMap, "clicked!", Toast.LENGTH_LONG)
            false
        }


    }

    ////////////////////////  구글맵 마커 여러개생성 및 띄우기 //////////////////////////
    fun manyMarker(jsonArr: JSONArray) {

        // for loop를 통한 n개의 마커 생성

        for (idx in 0 until jsonArr.length()) {
            // 1. 마커 옵션 설정 (만드는 과정)
            val makerOptions = MarkerOptions()
            val obj = jsonArr.getJSONObject(idx)


            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(LatLng(obj.getDouble("longitude"), obj.getDouble("latitude")))
                    .title(obj.getString("name"))
                    .snippet(obj.getString("information"))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .alpha(0.5f)
            // 2. 마커 생성 (마커를 나타냄)
            mMap!!.addMarker(makerOptions)
        }
        //정보창 클릭 리스너
        mMap!!.setOnInfoWindowClickListener(infoWindowClickListener)

        //마커 클릭 리스너
        mMap!!.setOnMarkerClickListener(markerClickListener)

        // 카메라를 위치로 옮긴다.
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(37.52758330340982, 126.9258400797844), 16f))
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            long = location.longitude
            lat = location.latitude
            //oneMarker(lat, long)

            VolleyService.GETVolley(this@GMap,
                /*"store?latitude=${location.latitude}&longitude=${location.longitude}"*/
                "store?latitude=126.9258400797844&longitude=37.52758330340982"
                , VolleyService.token) { testSuccess, response ->
                if (testSuccess) {
                    val jsonArr: JSONArray = JSONArray(response)
                    manyMarker(jsonArr)
                } else {
                    Toast.makeText(this@GMap, response, Toast.LENGTH_LONG).show()
                }
            }
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    companion object {
        fun newInstance(): GMap = GMap()
    }
}