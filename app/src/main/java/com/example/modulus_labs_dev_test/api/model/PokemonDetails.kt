package com.example.modulus_labs_dev_test.api.model

data class PokemonDetails(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val height: Int,
    val is_default: Boolean,
    val order: Int,
    val weight: String,
    val abilities: List<Any>,
    val forms: List<Any>,
    val game_indices: List<Any>,
    val held_items: List<Any>,
    val location_area_encounters: String,
    val moves: List<Any>,
    val past_types: List<Any>,
    val sprites: List<String>, //
    val cries: List<String>, //
    val species: List<Any>, //
    val stats: List<Any>,
    val types: List<Any>
)