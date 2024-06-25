package com.budgeteer.budgeteer.android.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class BudgeteerApiService : IntentService("BudgeteerApiService") {

    private lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service onCreate")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://devapibudgeteer.chhilif.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i(TAG, "Handling intent")

        val call = apiService.getHelloWorld()
        Log.d(TAG, "Request URL: ${call.request().url()}")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.string()
                    Log.d(TAG, "API response successful. Data: $responseData")
                    broadcastResult(responseData)
                } else {
                    val errorResponse = "Error: ${response.code()}"
                    Log.e(TAG, "API error: $errorResponse")
                    broadcastResult(errorResponse)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val errorMessage = "Failure: ${t.message}"
                Log.e(TAG, "API call failed: $errorMessage")
                broadcastResult(errorMessage)
            }
        })
    }

    private fun broadcastResult(result: String?) {
        Log.i(TAG, "Broadcasting result: $result")

        val broadcastIntent = Intent("com.budgeteer.budgeteer.RESULT")
        broadcastIntent.putExtra("result", result)
        sendBroadcast(broadcastIntent)
    }

    companion object {
        private const val TAG = "BudgeteerApiService"
    }
}