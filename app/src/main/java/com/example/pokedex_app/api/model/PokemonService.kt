package com.example.pokedex_app.api.model

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

    @GET("pokemon") // Change limit as needed. Default is limit = 20 and offset = 20
    suspend fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResponse

    @GET
    suspend fun getPokemonSprite(@Url url: String): PokemonSprite

    @GET("pokemon/{name}") // Fetch Pokémon details using its name
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetail

    companion object {fun create(): PokemonService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonService::class.java)
        }
    }
}