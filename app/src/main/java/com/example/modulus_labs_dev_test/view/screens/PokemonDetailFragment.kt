package com.example.modulus_labs_dev_test.view.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.modulus_labs_dev_test.R
import com.example.modulus_labs_dev_test.viewmodel.PokemonDetailViewModel
import java.util.Locale

class PokemonDetailFragment : Fragment(R.layout.layout_fragment_pokemon_details) {
    private val viewModel: PokemonDetailViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonImage = view.findViewById<ImageView>(R.id.pokemonImage_IV)
        val pokemonNameText = view.findViewById<TextView>(R.id.pokemonName_TV)
        val pokemonDetailsText = view.findViewById<TextView>(R.id.pokemonDetails_TV)

        val pokemonName = arguments?.getString("pokemonName") ?: return
//        viewModel.fetchPokemonDetails(pokemonName)

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { details ->
            details?.let {
//                Glide.with(requireContext()).load(it.sprite).into(pokemonImage)
                pokemonNameText.text = it.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                pokemonDetailsText.text = buildString {
                    append("Height: ")
                    append(it.height)
                    append("\t Weight: ")
                    append(it.weight)
                    append(" \t Base XP: ")
                    append(it.baseExperience)
                }
            }
        }
    }
}

