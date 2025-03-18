package com.example.modulus_labs_dev_test.repository

import com.example.modulus_labs_dev_test.api.model.PokemonService
import com.example.modulus_labs_dev_test.api.model.pokemondetails.PokemonDetail
import com.example.modulus_labs_dev_test.api.model.pokemonlist.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val service: PokemonService) {

    suspend fun fetchPokemon(offset: Int, limit: Int = 20): List<PokemonResult> {
        val response = service.getPokemon(offset, limit)
        return response.results
    }

    suspend fun fetchPokemonImage(name: String): String {
        return try {
            val details = service.getPokemonDetails(name)
            details.sprites.imageUrl.orEmpty()
        } catch (_: Exception) {
            ""
        }
    }

    suspend fun searchPokemonByName(name: String): PokemonDetail {
        return withContext(Dispatchers.IO) {
            service.getPokemonDetails(name.lowercase()) // Fetch Pokémon details properly
        }
    }
}