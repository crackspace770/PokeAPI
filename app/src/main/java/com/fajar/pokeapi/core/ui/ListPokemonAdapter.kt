package com.fajar.pokeapi.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajar.pokeapi.R
import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.utils.Constant.Companion.OFFICIAL_ARTWORK_URL
import com.fajar.pokeapi.databinding.PokemonItemBinding
import com.fajar.pokeapi.databinding.PokemonsItemsBinding

class ListPokemonAdapter : RecyclerView.Adapter<ListPokemonAdapter.ListViewHolder>() {

    var onItemClick: ((Pokemon) -> Unit)? = null

    private var listData = ArrayList<Pokemon>()


    fun setData(newListData: List<Pokemon>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonsItemsBinding.bind(itemView)
        fun bind(data: Pokemon) {
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
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pokemons_items, parent, false))
    }

    override fun getItemCount() = listData.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }


}