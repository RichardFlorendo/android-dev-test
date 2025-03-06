package com.example.modulus_labs_dev_test.view.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modulus_labs_dev_test.R
import com.example.modulus_labs_dev_test.api.model.PokemonService
import com.example.modulus_labs_dev_test.viewmodel.MainViewModel
import com.example.modulus_labs_dev_test.viewmodel.MainViewModelFactory
import com.example.modulus_labs_dev_test.viewmodel.recyclerview.PokemonAdapter

class PokemonFragment: Fragment(R.layout.layout_fragment_pokemon_screen){

    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory(PokemonService.create()) // Pass API service here
    }
    private lateinit var adapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        val loadingPanel = view.findViewById<View>(R.id.loadingPanel)
        val errorTextView = view.findViewById<TextView>(R.id.error_TV)
        val recyclerView = view.findViewById<RecyclerView>(R.id.pokemon_list_RV)

        val searchEditText = view.findViewById<TextView>(R.id.search_bar_ET)
        val searchButton = view.findViewById<Button>(R.id.search_bar_BT)

        val navigationButtonsLayout = view.findViewById<LinearLayout>(R.id.navigation_buttons_layout)
        val previousButton = view.findViewById<Button>(R.id.pokemon_previous_BT)
        val nextButton = view.findViewById<Button>(R.id.pokemon_next_BT)

        // Set up RecyclerView
        adapter = PokemonAdapter(emptyList()) { selectedPokemon ->
            val action = PokemonFragmentDirections.actionPokemonFragmentToPokemonDetailFragment(selectedPokemon.pokemonName)
            navController.navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchPokemon(query)
            }
        }

        // Observe the LiveData and update the RecyclerView dynamically
        viewModel.searchedPokemon.observe(viewLifecycleOwner) { pokemon ->
            pokemon?.let {
                adapter.updatePokemonList(listOf(it)) // Show only the searched Pokémon
                previousButton.visibility = View.INVISIBLE
                nextButton.visibility = View.INVISIBLE
            } ?: run {
                Toast.makeText(requireContext(), "Pokemon not found", Toast.LENGTH_SHORT).show()
            }
        }

        nextButton.setOnClickListener {
            viewModel.nextPage()
        }

        previousButton.setOnClickListener {
            viewModel.previousPage()
        }

        viewModel.pokemonState.observe(viewLifecycleOwner) { viewState ->
            when {
                viewState.loading -> {
                    loadingPanel.visibility = View.VISIBLE
                    errorTextView.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }

                viewState.error != null -> {
                    loadingPanel.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE

                    navigationButtonsLayout.visibility = View.GONE
                }

                else -> {
                    loadingPanel.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    // Update RecyclerView Adapter with new Pokémon list
                    adapter.updatePokemonList(viewState.list)

                    // Show navigation buttons only if there are more pages
                    if (viewModel.canNavigateNext() || viewModel.canNavigatePrevious()) {
                        navigationButtonsLayout.visibility = View.VISIBLE
                        previousButton.visibility = if (viewModel.canNavigatePrevious()) View.VISIBLE else View.GONE
                        nextButton.visibility = if (viewModel.canNavigateNext()) View.VISIBLE else View.GONE
                    } else {
                        navigationButtonsLayout.visibility = View.GONE
                    }

                }
            }
        }

        viewModel.fetchPokemon()
    }
}