package com.example.pokedex_app.viewmodel.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex_app.R
import com.example.pokedex_app.api.model.pokemonlist.PokemonUIModel

class PokemonAdapter(
    private var pokemonList: List<PokemonUIModel>,
    private val onItemClick: (PokemonUIModel) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemonItem = pokemonList[position]
        holder.pokemonName.text = pokemonItem.name
            .split(" ") //Split by spaces
            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } } //Capitalize each word

        //Load image using Coil instead of Glide
        holder.pokemonImage.load(pokemonItem.imageUrl) {
            placeholder(R.drawable.baseline_image_24) //Show placeholder while loading
            error(R.drawable.baseline_broken_image_24) //Show error image if load fails
            crossfade(true)
        }

        holder.itemView.setOnClickListener {
            onItemClick(pokemonItem)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    //Function to update data dynamically
    fun updatePokemonList(newList: List<PokemonUIModel>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name_TV)
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_pic_IV)
    }
}
