package com.example.pokedex_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedex_app.api.PokemonService
import com.example.pokedex_app.api.model.pokemondetails.PokemonDetail
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val service: PokemonService) : ViewModel() {
    private val _pokemonDetail = MutableLiveData<PokemonDetail?>()
    val pokemonDetail: LiveData<PokemonDetail?> = _pokemonDetail

    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            try {
                val details = service.getPokemonDetails(name.lowercase()) // Fetch details properly
                _pokemonDetail.value = details
            } catch (e: Exception) {
                _pokemonDetail.value = null
            }
        }
    }
}

class PokemonDetailViewModelFactory(private val service: PokemonService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonDetailViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}