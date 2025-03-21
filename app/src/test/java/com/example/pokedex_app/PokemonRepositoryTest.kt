package com.example.pokedex_app

import com.example.pokedex_app.api.model.PokemonService
import com.example.pokedex_app.api.model.pokemonlist.PokemonResponse
import com.example.pokedex_app.api.model.pokemonlist.PokemonResult
import com.example.pokedex_app.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PokemonRepositoryTest {

    private lateinit var repository: PokemonRepository
    private val service = mockk<PokemonService>()

    @Before
    fun setup() {
        repository = PokemonRepository(service)
    }

    @Test
    fun fetchPokemonlist() = runBlocking {
        val mockResponse = PokemonResponse(2, null, null, listOf(
            PokemonResult("Charmander", "url"),
            PokemonResult("Squirtle", "url")
        ))

        coEvery { service.getPokemon(0, 20) } returns mockResponse

        val result = repository.fetchPokemon(0, 20)

        assertEquals(2, result.size)
        assertEquals("Charmander", result[0].name)
        assertEquals("Squirtle", result[1].name)
    }
}