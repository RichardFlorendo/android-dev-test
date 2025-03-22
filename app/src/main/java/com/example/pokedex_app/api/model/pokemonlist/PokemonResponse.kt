package com.example.pokedex_app.api.model.pokemonlist

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)