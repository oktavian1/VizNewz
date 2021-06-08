package com.example.viznews.data.network.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiServices {

    fun create(): Services{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://promising-idea-314607.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpBuilder)
            .build()
        return retrofit.create(Services::class.java)
    }

    private val httpBuilder = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(defaultHTTPClient())
        .readTimeout(120, TimeUnit.SECONDS)
        .build()


    @Throws(IOException::class)
    private fun defaultHTTPClient(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

}