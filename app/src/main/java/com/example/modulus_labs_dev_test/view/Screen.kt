package com.example.modulus_labs_dev_test.view

sealed class Screen(val route: String) {
    object PokemonScreen: Screen(route = "pokemonscreen")
    object PokemonDetailScreen: Screen(route = "pokemondetailscreen")
}