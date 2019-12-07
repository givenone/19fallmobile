package com.example.ezorder

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class store_location : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var geocoder: Geocoder? = null
    private var button : Button? = null
    private var next_button : Button? = null
    private var editText : EditText? = null
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_location)

        button = findViewById(R.id.map_store_search_button) as Button
        next_button = findViewById(R.id.map_store_button) as Button
        editText = findViewById(R.id.store_location_editText) as EditText
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = getSupportFragmentManager().findFragmentById(R.id.map_store_location) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    var markerClickListener: GoogleMap.OnMarkerClickListener =
        GoogleMap.OnMarkerClickListener { marker ->
            val markerId = marker.id
            //선택한 타겟위치
            val location = marker.position

            val intent = Intent(this@store_location,StoreSign::class.java);
            var longitude = location.latitude
            var latitude = location.longitude
            intent.putExtra("longitude", longitude)
            intent.putExtra("latitude", latitude)
            intent.setFlags(intent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent);

            false
        }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap:GoogleMap) {
        mMap = googleMap
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.52487, 126.92723), 16f))
        geocoder = Geocoder(this)
        // 맵 터치 이벤트 구현 //
        mMap!!.setOnMapClickListener { point ->
            val mOptions = MarkerOptions()
            // 마커 타이틀
            mOptions.title("마커 좌표")
            val latitude = point.latitude // 위도
            val longitude = point.longitude // 경도
            // 마커의 스니펫(간단한 텍스트) 설정
            mOptions.snippet("$latitude, $longitude")
            // LatLng: 위도 경도 쌍을 나타냄
            mOptions.position(LatLng(latitude, longitude))
            // 마커(핀) 추가
            googleMap.addMarker(mOptions)
        }

        ////////////////////
        // 버튼 이벤트
        button!!.setOnClickListener {
            val str = editText!!.text.toString()
            Toast.makeText(
                this@store_location,
                str, Toast.LENGTH_SHORT
            ).show()
            var addressList:List<Address>? = null
            try {
                // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                addressList = geocoder!!.getFromLocationName(
                    str, // 주소
                    10) // 최대 검색 결과 개수

                val splitStr = addressList!!.get(0).toString().split(",")
                val address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length - 2) // 주소
                println(address)
                val latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1) // 위도
                val longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1) // 경도
                println(latitude)
                println(longitude)
                // 좌표(위도, 경도) 생성
                val point = LatLng(java.lang.Double.parseDouble(latitude), java.lang.Double.parseDouble(longitude))
                // 마커 생성
                val mOptions2 = MarkerOptions()
                mOptions2.title("search result")
                mOptions2.snippet(address)
                mOptions2.position(point)
                // 마커 추가
                mMap!!.addMarker(mOptions2)
                // 해당 좌표로 화면 줌
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15F))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            // 콤마를 기준으로 split
        }

        mMap!!.setOnMarkerClickListener(markerClickListener)
    }


}
