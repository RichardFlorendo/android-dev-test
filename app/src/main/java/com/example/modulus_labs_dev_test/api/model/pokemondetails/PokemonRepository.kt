package com.example.modulus_labs_dev_test.repository

import android.util.Log
import com.example.modulus_labs_dev_test.api.model.PokemonService
import com.example.modulus_labs_dev_test.api.model.pokemondetails.PokemonDetail
import com.example.modulus_labs_dev_test.api.model.pokemonlist.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val service: PokemonService) {

    suspend fun fetchPokemon(): List<PokemonResult> {
        return withContext(Dispatchers.IO) {
            service.getPokemon().results
        }
    }

    suspend fun fetchPokemonImage(url: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getPokemonSprite(url)
                Log.d("DEBUG_API_RESPONSE", "API Response: $response")

                val spriteUrl = service.getPokemonSprite(url).sprites.imageUrl
                Log.d("DEBUG_IMAGE_URL", "Fetched image URL: $spriteUrl") // Log the correct sprite URL
                spriteUrl
            } catch (e: Exception) {
                Log.e("DEBUG_IMAGE_ERROR", "Error fetching image: ${e.message}")
                null
            }.toString()
        }
    }

    suspend fun searchPokemonByName(name: String): PokemonDetail {
        return withContext(Dispatchers.IO) {
            service.getPokemonDetails(name.lowercase()) // Fetch Pokémon details properly
        }
    }
}