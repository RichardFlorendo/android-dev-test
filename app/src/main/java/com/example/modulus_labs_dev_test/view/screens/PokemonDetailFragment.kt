package com.example.modulus_labs_dev_test.view.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.modulus_labs_dev_test.R
import com.example.modulus_labs_dev_test.api.model.PokemonService
import com.example.modulus_labs_dev_test.viewmodel.PokemonDetailViewModel
import com.example.modulus_labs_dev_test.viewmodel.PokemonDetailViewModelFactory
import java.util.Locale

class PokemonDetailFragment : Fragment(R.layout.layout_fragment_pokemon_details) {
    private val viewModel: PokemonDetailViewModel by viewModels{
        PokemonDetailViewModelFactory(PokemonService.create())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonImage = view.findViewById<ImageView>(R.id.pokemonImage_IV)
        val pokemonNameText = view.findViewById<TextView>(R.id.pokemonName_TV)
        val pokemonDetailsText = view.findViewById<TextView>(R.id.pokemonDetails_TV)

        val pokemonName = arguments?.getString("pokemonName") ?: return
        viewModel.fetchPokemonDetails(pokemonName)

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { details ->
            details?.let {
                val spriteUrl = it.sprites.imageUrl // Correct reference to the sprite
                Glide.with(requireContext()).load(spriteUrl).into(pokemonImage)

                pokemonNameText.text = it.name.replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                }

                pokemonDetailsText.text = buildString {
                    append("Height: ${it.height} \nWeight: ${it.weight} \nBase XP: ${it.baseExperience}")
                }
            }
        }
    }
}

