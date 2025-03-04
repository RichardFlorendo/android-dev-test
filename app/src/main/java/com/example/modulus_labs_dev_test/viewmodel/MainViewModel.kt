package com.example.modulus_labs_dev_test.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modulus_labs_dev_test.api.model.PokemonResponse
import com.example.modulus_labs_dev_test.api.model.PokemonResult
import com.example.modulus_labs_dev_test.api.pokemonService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _pokemonState = mutableStateOf(PokemonState()) //checks the states of values on PokemonScreen
    val pokemonState: State<PokemonState> = _pokemonState //updates the state when data is gotten

    init { //Automatically starts everytime MainViewModel is loaded in PokemonScreen
        fetchPokemon()
        Log.d("INIT", "INIT")
    }

    private fun fetchPokemon(){
        viewModelScope.launch {// launches a coroutine (suspend functions or asynchronous)
            try { //best oractice for internet GET and PUSH
                Log.d("API_CALL", "Fetching Pokémon data...")
                val response = pokemonService.getPokemon() //calling the suspend function for internet gotten data
                Log.d("API_RESPONSE", "Received: $response")
                Log.d("RESPONSE2", response.toString())

                _pokemonState.value = _pokemonState.value.copy( //"Get the current state and change the states"
                    list = response.results, //populates list
                    loading = false, //not loading
                    error = null //no error
                )
            }
            catch (e: Exception){
                Log.e("API_ERROR", "Error fetching Pokémon", e)
                _pokemonState.value = _pokemonState.value.copy(
                    loading = false, //not loading anymore, but with error
                    error = "Error fetching Pokemon ${e.message}"
                )
            }
        }
    }

    data class PokemonState( //contains loading status, if list is not empty, and if there is error
        val loading: Boolean = true,
        val list: List<PokemonResult> = emptyList(),
        val error: String? = null
    )
}