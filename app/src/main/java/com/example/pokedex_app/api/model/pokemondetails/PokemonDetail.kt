package com.example.pokedex_app.api.model.pokemondetails

import com.google.gson.annotations.SerializedName

data class PokemonDetail (
    val id: Int,
    val name: String,
    @SerializedName("base_experience") val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default") val imageUrl: String?
)