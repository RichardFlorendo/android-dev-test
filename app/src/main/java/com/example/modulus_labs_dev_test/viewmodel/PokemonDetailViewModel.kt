package com.example.modulus_labs_dev_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modulus_labs_dev_test.api.model.pokemondetails.PokemonDetail
import com.example.modulus_labs_dev_test.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonDetail = MutableLiveData<PokemonDetail?>()
    val pokemonDetail: LiveData<PokemonDetail?> = _pokemonDetail

//    fun fetchPokemonDetails(name: String) {
//        viewModelScope.launch {
//            try {
//                val details = repository.fetchPokemonDetails(name)
//                _pokemonDetail.value = details
//            } catch (e: Exception) {
//                _pokemonDetail.value = null
//            }
//        }
//    }
}