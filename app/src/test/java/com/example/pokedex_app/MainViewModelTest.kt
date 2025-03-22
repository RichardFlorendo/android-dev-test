package com.example.pokedex_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.pokedex_app.api.PokemonService
import com.example.pokedex_app.api.model.pokemondetails.PokemonDetail
import com.example.pokedex_app.api.model.pokemondetails.Sprites
import com.example.pokedex_app.api.model.pokemonlist.PokemonResponse
import com.example.pokedex_app.api.model.pokemonlist.PokemonResult
import com.example.pokedex_app.api.model.pokemondetails.PokemonRepository
import com.example.pokedex_app.viewmodel.MainViewModel
import com.example.pokedex_app.viewmodel.recyclerview.PokemonRecyclerItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule() // Allows LiveData to be tested

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: PokemonRepository
    private val service = mockk<PokemonService>(relaxed = true) // Mock API service
    private val testDispatcher = UnconfinedTestDispatcher() // Test Coroutine Dispatcher

    // Extension function to observe LiveData and retrieve its value
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        try {
            if (!latch.await(time, timeUnit)) {
                throw RuntimeException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set main dispatcher for testing
        repository = PokemonRepository(service)
        viewModel = MainViewModel(service, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset dispatcher after tests
    }

    @Test
    fun fetchPokemonTest() = runTest {
        // Mock Pokémon list response
        val mockPokemonList = listOf(
            PokemonResult(name = "ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/")
        )
        coEvery { service.getPokemon(0, 20) } returns PokemonResponse(
            count = 1,
            next = "",
            previous = "",
            results = mockPokemonList
        )

        // Mock Pokémon detail response
        coEvery { service.getPokemonDetails("ivysaur") } returns PokemonDetail(
            id = 2,
            name = "ivysaur",
            baseExperience = 50,
            height = 10,
            weight = 130,
            sprites = Sprites(imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png")
        )

        viewModel.fetchPokemon(0) // Call the function

        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutine completes

        // Assert that the LiveData was updated correctly
        val result = viewModel.pokemonState.getOrAwaitValue().list
        assertNotNull(result) // Ensure list is not null
        assertEquals(
            listOf(
                PokemonRecyclerItem(
                    pokemonName = "ivysaur",
                    pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"
                )
            ),
            result
        )
    }

    @Test
    fun searchPokemonTest() = runTest {
        // Mock API response
        val mockPokemonDetail = PokemonDetail(
            id = 25,
            name = "pikachu",
            baseExperience = 112,
            height = 4,
            weight = 60,
            sprites = Sprites(imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
        )

        coEvery { service.getPokemonDetails("pikachu") } returns mockPokemonDetail

        // Call function
        viewModel.searchPokemon("pikachu")

        // Ensure coroutine execution
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify API was called
        coVerify(exactly = 1) { service.getPokemonDetails("pikachu") }

        // Wait for LiveData update
        val result = viewModel.searchedPokemon.getOrAwaitValue()
        assertNotNull(result) // Ensure it is not null
        assertEquals(
            PokemonRecyclerItem(
                pokemonName = "Pikachu",
                pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
            ),
            result
        )
    }
}
