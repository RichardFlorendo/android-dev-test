package com.example.modulus_labs_dev_test.api.model

import com.example.modulus_labs_dev_test.api.model.pokemondetails.PokemonDetail
import com.example.modulus_labs_dev_test.api.model.pokemonlist.PokemonResponse
import com.example.modulus_labs_dev_test.api.model.pokemonlist.PokemonSprite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonService {

    @GET("pokemon?limit=10") // Change limit as needed
    suspend fun getPokemon(): PokemonResponse

    @GET
    suspend fun getPokemonSprite(@Url url: String): PokemonSprite

        @GET("pokemon/{name}") // Fetch Pokémon details using its name
        suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetail

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        fun create(): PokemonService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonService::class.java)
        }
    }
}