package com.example.pokedex_app.api.model.pokemonlist

import com.google.gson.annotations.SerializedName

data class PokemonResult(
    val name: String,
    val url: String
)

// Add a new model for detailed Pokémon data
data class PokemonSprite(
    @SerializedName("sprites") val sprites: Sprites
)

// Sprites model to get the front_default image
data class Sprites(
    @SerializedName("front_default") val imageUrl: String?
)
