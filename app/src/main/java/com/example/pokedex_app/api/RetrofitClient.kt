package com.example.pokedex_app.api

import com.example.pokedex_app.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.POKEAPI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}