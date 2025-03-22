package com.example.pokedex_app.api.model.pokemondetails

import com.example.pokedex_app.api.model.pokemonlist.PokemonUIModel
import com.google.gson.annotations.SerializedName

data class PokemonDetail (
    val id: Int,
    val name: String,
    @SerializedName("base_experience") val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val sprites: Sprites
) {
    fun toUIModel(): PokemonUIModel {
        return PokemonUIModel(
            name = name.replaceFirstChar { it.uppercaseChar() }, // Capitalize name
            imageUrl = sprites.imageUrl.orEmpty() // Get the main image URL
        )
    }
}

data class Sprites(
    @SerializedName("front_default") val imageUrl: String?
)