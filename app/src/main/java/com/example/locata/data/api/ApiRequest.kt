package com.example.locata.data.api

import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

abstract class ApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        try {
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("success"))
                    } catch (e: JSONException) {
                    }
                    message.append("\n")
                }
                message.append("Error code : ${response.code()}")
                throw IOException(message.toString())
            }
        }catch (e: Exception){
            Toast.makeText(throw e , "$e", Toast.LENGTH_SHORT).show()
        }
    }

}