package com.example.modulus_labs_dev_test.viewmodel.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.modulus_labs_dev_test.R

class PokemonAdapter(
    private var pokemonList: List<PokemonRecyclerItem>,
    private val onItemClick: (PokemonRecyclerItem) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemonItem = pokemonList[position]
        holder.pokemonName.text = pokemonItem.pokemonName

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(pokemonItem.pokemonImage?.takeIf { it.isNotEmpty() })
            .placeholder(R.drawable.baseline_image_24) // Set a placeholder while loading
            .error(R.drawable.baseline_broken_image_24) // Set an error image if load fails
            .into(holder.pokemonImage)

        holder.itemView.setOnClickListener {
            onItemClick(pokemonItem)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    // Function to update data dynamically
    fun updatePokemonList(newList: List<PokemonRecyclerItem>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name_TV)
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_pic_IV)
    }
}
