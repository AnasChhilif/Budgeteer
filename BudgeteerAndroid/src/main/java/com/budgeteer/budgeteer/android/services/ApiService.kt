package com.budgeteer.budgeteer.android.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    fun getHelloWorld(): Call<ResponseBody>
}