package com.example.modulus_labs_dev_test.view.screens

import PokemonDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.modulus_labs_dev_test.viewmodel.MainViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.modulus_labs_dev_test.api.model.PokemonResponse
import com.example.modulus_labs_dev_test.view.Screen

@Composable
fun PokemonApp(navController: NavHostController){
    var pokemonViewModel: MainViewModel = viewModel()
    val viewState by pokemonViewModel.pokemonState

    NavHost(navController = navController,
        startDestination = Screen.PokemonScreen.route){
        composable(route = Screen.PokemonScreen.route){
            PokemonScreen(viewState = viewState,
                navigateToDetail = {
                    //This part is responsible for passing data from the current screen to the details screen
                    //It utilizes he savedStateHandle, which is a component of the Compose navigation framework
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("poke", it) //Stores the pokemon as "poke"
                    navController.navigate(Screen.PokemonDetailScreen.route) //navigates to the details screen
                })
        }

        composable(route = Screen.PokemonDetailScreen.route){
            val pokemon = navController.previousBackStackEntry //retrieves data from the previous backstack
                ?.savedStateHandle //stores data across screens
                ?.get<PokemonResponse>("poke") //retrieve data with type "Pokemon" with key "poke"
                ?: PokemonResponse(
                    0,
                    "",
                    "",
                    emptyList()
                ) //if "poke" is empty, create an empty Pokemon
            PokemonDetailScreen(pokemon = pokemon)
        }
    }
}