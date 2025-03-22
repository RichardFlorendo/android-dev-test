package com.example.pokedex_app.api

import com.example.pokedex_app.BuildConfig
import com.example.pokedex_app.api.model.pokemondetails.PokemonDetail
import com.example.pokedex_app.api.model.pokemonlist.PokemonResponse
import com.example.pokedex_app.api.model.pokemonlist.PokemonSprite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResponse

    @GET
    suspend fun getPokemonSprite(@Url url: String): PokemonSprite

    @GET("pokemon/{name}") //Fetch Pokémon details using its name
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetail

    companion object {
        fun create(): PokemonService {
            return RetrofitClient.instance.create(PokemonService::class.java)
        }
    }
}