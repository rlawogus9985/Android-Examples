package com.example.dallamain.Manager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://61.80.148.23:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}