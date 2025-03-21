package com.example.pokedex_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_app.api.model.PokemonService
import com.example.pokedex_app.repository.PokemonRepository
import com.example.pokedex_app.viewmodel.recyclerview.PokemonRecyclerItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    service: PokemonService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    private val repository = PokemonRepository(service)

    private val _searchedPokemon = MutableLiveData<PokemonRecyclerItem?>()
    val searchedPokemon: LiveData<PokemonRecyclerItem?> = _searchedPokemon

    private val _pokemonState = MutableLiveData<PokemonState>()
    val pokemonState: LiveData<PokemonState> = _pokemonState

    private var _allPokemonList: List<PokemonRecyclerItem> = emptyList() // Store all Pokémon

    private var currentOffset = 0
    private val pageSize = 20 // Default PokeAPI page size

    data class PokemonState(
        val loading: Boolean = false,
        val error: String? = null,
        val list: List<PokemonRecyclerItem> = emptyList()
    )

    fun fetchPokemon(offset: Int = currentOffset) {
        viewModelScope.launch{
            _pokemonState.value = PokemonState(loading = true)

            try {
                val pokemonList = repository.fetchPokemon(offset = offset, limit = 20)
                // Fetch images for each Pokémon
                val pokemonItems = pokemonList.map { pokemon ->
                    val pokemonName = pokemon.name
                    val imageUrl = repository.fetchPokemonImage(pokemonName)
                    PokemonRecyclerItem(pokemonName, imageUrl)
                }

                _allPokemonList = pokemonItems // Store original list

                _pokemonState.value = PokemonState(
                    loading = false,
                    error = null,
                    list = pokemonItems
                )
            } catch (e: Exception) {
                _pokemonState.value = PokemonState(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchPokemonDetails(){

    }

    fun nextPage() {
        currentOffset += pageSize
        fetchPokemon(currentOffset)
    }

    fun previousPage() {
        if (currentOffset >= pageSize) {
            currentOffset -= pageSize
            fetchPokemon(currentOffset)
        }
    }

    fun canNavigatePrevious(): Boolean {
        return currentOffset > 0
    }

    fun canNavigateNext(): Boolean {
        return _allPokemonList.size == 20 // PokeAPI returns 20 per page, meaning more pages exist
    }

    fun searchPokemon(name: String) {
        viewModelScope.launch(dispatcher) {
            try {

                val details = repository.searchPokemonByName(name)

                _searchedPokemon.postValue(
                    PokemonRecyclerItem(
                        pokemonName = details.name.replaceFirstChar { it.uppercaseChar() },
                        pokemonImage = details.sprites.imageUrl ?: ""
                    )
                )
            } catch (_: Exception) {
                _searchedPokemon.postValue(null)
            }
        }
    }
}