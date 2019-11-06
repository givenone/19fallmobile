package com.example.ezorder

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject



object VolleyService {
    var token = ""
    val testUrl = "http://192.168.0.9:8000/"

    fun GETVolley(context: Context, url : String, thetoken : String, res: (Boolean, String) -> Unit) {

        val testRequest = object : StringRequest(Method.GET, testUrl + url , Response.Listener { response ->
            println("서버 Response 수신: $response")
            res(true, response)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "서버 Response 가져오기 실패: $error")
            res(false, error.toString())
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = "Token " + VolleyService.token
                return params
            }
        }

        Volley.newRequestQueue(context).add(testRequest)
    }

    @ExperimentalStdlibApi
    fun POSTVolley(context: Context, url : String, parameterList : HashMap<String, String>, res: (Boolean, String) -> Unit) {


        val testRequest = object : StringRequest(Method.POST, testUrl + url , Response.Listener { response ->
            println("서버 Response 수신: $response")
            res(true, response)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "서버 Response 가져오기 실패: $error")
            res(false, error.toString())
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                // TODO :: what is needed?
                return parameterList
            }
            // getBodyContextType에서는 요청에 포함할 데이터 형식을 지정한다.
            // getBody에서는 요청에 JSON이나 String이 아닌 ByteArray가 필요하므로, 타입을 변경한다.
        }


        Volley.newRequestQueue(context).add(testRequest)
    }
}