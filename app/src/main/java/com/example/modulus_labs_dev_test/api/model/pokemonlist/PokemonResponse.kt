package com.example.modulus_labs_dev_test.api.model.pokemonlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PokemonResponse( //Needed to be set up to adhere to the JSON list source
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: @RawValue List<PokemonResult>
): Parcelable
