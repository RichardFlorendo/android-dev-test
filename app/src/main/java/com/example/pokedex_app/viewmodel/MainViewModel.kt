package com.example.pokedex_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_app.api.PokemonService
import com.example.pokedex_app.api.model.pokemonlist.PokemonUIModel
import com.example.pokedex_app.api.model.pokemondetails.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    service: PokemonService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val repository = PokemonRepository(service)

    private val _searchedPokemon = MutableLiveData<PokemonUIModel?>()
    val searchedPokemon: LiveData<PokemonUIModel?> = _searchedPokemon

    private val _pokemonState = MutableLiveData<PokemonState>()
    val pokemonState: LiveData<PokemonState> = _pokemonState

    private var _allPokemonList: List<PokemonUIModel> = emptyList() // Store all Pokémon
    private var currentOffset = 0
    private val pageSize = 20 // Default PokeAPI page size

    data class PokemonState(
        val loading: Boolean = false,
        val error: String? = null,
        val list: List<PokemonUIModel> = emptyList()
    )

    fun fetchPokemon(offset: Int = currentOffset) {
        viewModelScope.launch{
            _pokemonState.value = PokemonState(loading = true)

            try {
                val pokemonUIList = repository.fetchPokemon(offset = offset, limit = pageSize)

                //Fetch images for each Pokémon
                _allPokemonList = pokemonUIList //Store original list

                _pokemonState.postValue(PokemonState(
                    loading = false,
                    error = null,
                    list = pokemonUIList
                ))
            } catch (e: Exception) {
                _pokemonState.postValue(PokemonState(
                    loading = false,
                    error = e.message
                ))
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

                _searchedPokemon.postValue(details.toUIModel())
            } catch (_: Exception) {
                _searchedPokemon.postValue(null)
            }
        }
    }
}