package com.example.pokedex_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex_app.api.PokemonService

class MainViewModelFactory(private val service: PokemonService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}