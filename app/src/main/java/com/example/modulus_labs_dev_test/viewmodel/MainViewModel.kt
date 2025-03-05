package com.example.modulus_labs_dev_test.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modulus_labs_dev_test.api.model.PokemonService
import com.example.modulus_labs_dev_test.repository.PokemonRepository
import com.example.modulus_labs_dev_test.viewmodel.recyclerview.PokemonRecyclerItem
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel(service: PokemonService) : ViewModel() {
    private val repository = PokemonRepository(service)

    private val _searchedPokemon = MutableLiveData<PokemonRecyclerItem?>()
    val searchedPokemon: LiveData<PokemonRecyclerItem?> = _searchedPokemon

    private val _pokemonState = MutableLiveData<PokemonState>()
    val pokemonState: LiveData<PokemonState> = _pokemonState

    private var _allPokemonList: List<PokemonRecyclerItem> = emptyList() // Store all Pokémon

    data class PokemonState(
        val loading: Boolean = false,
        val error: String? = null,
        val list: List<PokemonRecyclerItem> = emptyList()
    )

    fun fetchPokemon() {
        viewModelScope.launch{
            _pokemonState.value = PokemonState(loading = true)

            try {
                val pokemonList = repository.fetchPokemon()
                // Fetch images for each Pokémon
                val pokemonItems = pokemonList.map { pokemon ->
                    val imageUrl = repository.fetchPokemonImage(pokemon.url)
                    Log.d("DEBUG_VIEWMODEL", "Creating PokemonRecyclerItem: Name=${pokemon.name}, Image=${imageUrl}")

                    PokemonRecyclerItem(pokemon.name, imageUrl)
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

    fun searchPokemon(name: String) {
        viewModelScope.launch {
            try {
                val details = repository.searchPokemonByName(name)
                details?.let { it ->
                    _searchedPokemon.value = PokemonRecyclerItem(
                        pokemonName = it.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        },
                        pokemonImage = it.sprites.imageUrl ?: ""
                    )
                } ?: run {
                    _searchedPokemon.value = null
                }
            } catch (e: Exception) {
                _searchedPokemon.value = null
            }
        }
    }
}