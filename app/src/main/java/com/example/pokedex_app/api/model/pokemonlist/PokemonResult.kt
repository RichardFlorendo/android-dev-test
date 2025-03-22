package com.example.pokedex_app.api.model.pokemonlist

import com.google.gson.annotations.SerializedName

data class PokemonResult(
    val name: String,
    val url: String
) {
    fun toUIModel(): PokemonUIModel {
        val pokemonId = url.trimEnd('/').split("/").last() //Extract ID from URL
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

        return PokemonUIModel(
            name = name.replaceFirstChar { it.uppercaseChar() }, //Capitalize name
            imageUrl = imageUrl
        )
    }
}

// Add a new model for detailed Pokémon data
data class PokemonSprite(
    @SerializedName("sprites") val sprites: Sprites
)

// Sprites model to get the front_default image
data class Sprites(
    @SerializedName("front_default") val imageUrl: String?
)
