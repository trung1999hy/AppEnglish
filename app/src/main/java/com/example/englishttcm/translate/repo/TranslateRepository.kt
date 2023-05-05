package com.example.englishttcm.translate.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class TranslateRepository(_application: Application) {
    private var application:Application
    private val url = "https://google-translate1.p.rapidapi.com/language/translate/v2"

    private val lang = arrayOf("en","vi")
    init{
        application = _application
    }
    fun getTranslateText(srcLangId: Int, tarLangId:Int, sourceText: String):MutableLiveData<String>{
        val queue = Volley.newRequestQueue(application.baseContext)
        var tranText = MutableLiveData<String>()
        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val json = JSONObject(response)
                tranText.value = json.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText")
//                Toast.makeText(application, "complete", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Log.d("TranslateFrag", error.networkResponse.statusCode.toString())
//                Toast.makeText(application, error.networkResponse.statusCode.toString(), Toast.LENGTH_SHORT).show()

            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["source"] = lang[srcLangId]
                params["target"] = lang[tarLangId]
                params["q"] = sourceText
                return params
            }
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["content-type"] = "application/x-www-form-urlencoded"
                headers["accept-encoding"] = "application/gzip"
                headers["x-rapidapi-key"] = "1729b92335msh97ef4c8466b7755p169fb8jsnb3e64b781708"
                headers["x-rapidapi-host"] = "google-translate1.p.rapidapi.com"
                return headers
            }
        }
        queue.add(stringRequest)
        return tranText
    }

}