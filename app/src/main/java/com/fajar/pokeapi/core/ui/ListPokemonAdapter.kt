package com.fajar.pokeapi.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fajar.pokeapi.R
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import com.fajar.pokeapi.databinding.PokemonItemBinding

class ListPokemonAdapter : ListAdapter<PokemonResponse, ListPokemonAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((PokemonResponse) -> Unit)? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonItemBinding.bind(itemView)
        fun bind(data: PokemonResponse) {
            with(binding) {
                tvPokeName.text = data.name
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonResponse>() {
            override fun areItemsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}