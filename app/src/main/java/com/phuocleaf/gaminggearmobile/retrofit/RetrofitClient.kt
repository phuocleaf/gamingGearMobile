package com.phuocleaf.gaminggearmobile.retrofit

import com.phuocleaf.gaminggearmobile.util.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api:GamingGearAPI by lazy {
        Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GamingGearAPI::class.java)
    }
}