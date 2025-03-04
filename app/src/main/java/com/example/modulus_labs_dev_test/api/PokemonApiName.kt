package com.example.modulus_labs_dev_test.api

import com.example.modulus_labs_dev_test.api.model.PokemonResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/") //baseURL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val pokemonService = retrofit.create(PokemonApiName::class.java)

interface PokemonApiName{
    @GET("pokemon?limit=20&offset=0") //appended to the end of baseURL
    suspend fun getPokemon() : PokemonResponse //Adds to the Pokemon.kt list
    //the entire JSON list from the URL, stored in the Pokemon list
    }