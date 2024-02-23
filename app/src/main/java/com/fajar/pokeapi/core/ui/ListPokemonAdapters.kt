package com.fajar.pokeapi.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajar.pokeapi.R
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonsResponse
import com.fajar.pokeapi.databinding.PokemonItemBinding
import com.fajar.pokeapi.databinding.PokemonsItemsBinding
import com.fajar.pokeapi.core.utils.Constant
import com.fajar.pokeapi.core.utils.Constant.Companion.OFFICIAL_ARTWORK_URL

class ListPokemonAdapters : ListAdapter<PokemonsResponse, ListPokemonAdapters.ListViewHolder>(DIFF_CALLBACK)  {

    var onItemClick: ((PokemonsResponse) -> Unit)? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonsItemsBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bind(data: PokemonsResponse) {
            with(binding) {

                val officialArtworkUrl = "${OFFICIAL_ARTWORK_URL}${position + 1}.png"

                tvPokemon.text = data.name
                Glide.with(itemView.context)
                    .load(officialArtworkUrl)
                    .into(imgPokemons)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pokemons_items, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonsResponse>() {
            override fun areItemsTheSame(oldItem: PokemonsResponse, newItem: PokemonsResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PokemonsResponse, newItem: PokemonsResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

}