package com.example.pokedex_app.view.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.pokedex_app.R
import com.example.pokedex_app.api.PokemonService
import com.example.pokedex_app.databinding.LayoutFragmentPokemonDetailsBinding
import com.example.pokedex_app.viewmodel.PokemonDetailViewModel
import com.example.pokedex_app.viewmodel.PokemonDetailViewModelFactory
import java.util.Locale

class PokemonDetailFragment : Fragment(R.layout.layout_fragment_pokemon_details) {
    private val viewModel: PokemonDetailViewModel by viewModels{
        PokemonDetailViewModelFactory(PokemonService.create())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = LayoutFragmentPokemonDetailsBinding.bind(view) //Initialize ViewBinding

        val pokemonImage = binding.pokemonImageIV
        val pokemonNameText = binding.pokemonNameTV
        val pokemonDetailsText = binding.pokemonDetailsTV

        val pokemonName = arguments?.getString("pokemonName") ?: return
        viewModel.fetchPokemonDetails(pokemonName)

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { details ->
            details?.let {
                val spriteUrl = it.sprites.imageUrl
                val imageLoader = ImageLoader(requireContext())
                val request = ImageRequest.Builder(requireContext())
                    .data(spriteUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.baseline_image_24) // Placeholder while loading
                    .error(R.drawable.baseline_broken_image_24) // Error image if loading fails
                    .target(pokemonImage)
                    .build()

                imageLoader.enqueue(request)

                pokemonNameText.text = it.name.replaceFirstChar { char ->
                    if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                }
                Log.e("", "")
                pokemonDetailsText.text = buildString {
                    append("Height: ${it.height} \nWeight: ${it.weight} \nBase XP: ${it.baseExperience}")
                }
            }
        }
    }
}

