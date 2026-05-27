package com.example.appclientepedido_vesp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAppApi {

    private const val BASE_URL = "https://api.mockfly.dev/mocks/f48ca9d0-3fef-4231-ac49-ca0a72e5cee1/"

    val apiService: IService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IService::class.java)

    }

}