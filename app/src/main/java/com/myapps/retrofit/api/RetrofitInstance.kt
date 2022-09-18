package com.myapps.retrofit.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        val BASE_URL="https://gorest.co.in/public/v2/"

        fun getRetrofitInstance():Retrofit{
            val loggingInterceptor=HttpLoggingInterceptor()
            loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
            val client=OkHttpClient.Builder()
            client.addInterceptor(loggingInterceptor)
            return Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(
                GsonConverterFactory.create()
            ).client(client.build()
            ).build()
        }
    }
}