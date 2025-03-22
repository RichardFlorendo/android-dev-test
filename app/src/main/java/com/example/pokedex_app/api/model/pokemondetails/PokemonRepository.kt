package com.example.pokedex_app.api.model.pokemondetails

import com.example.pokedex_app.api.PokemonService
import com.example.pokedex_app.api.model.pokemonlist.PokemonUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val service: PokemonService) {

    suspend fun fetchPokemon(offset: Int, limit: Int = 20): List<PokemonUIModel> {
        val response = service.getPokemon(offset, limit)
        return response.results.map { it.toUIModel() }
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
            service.getPokemonDetails(name.lowercase()) //Fetch Pokémon details properly
        }
    }
}