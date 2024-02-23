package com.fajar.pokeapi.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fajar.pokeapi.R
import com.fajar.pokeapi.core.data.remote.response.Type
import com.fajar.pokeapi.core.data.remote.response.TypesItem
import com.fajar.pokeapi.databinding.TypeItemBinding

class TypeAdapter: RecyclerView.Adapter<TypeAdapter.TypesViewHolder>() {

    inner class TypesViewHolder(private val binding: TypeItemBinding) : ViewHolder(binding.root) {
        fun bind(type: TypesItem){
            binding.apply {

                val colorResId = when (type.type.name) {
                    "normal" -> R.color.normal_color
                    "fire" -> R.color.fire_color
                    "water" -> R.color.water_color
                    "electric" -> R.color.electric_color
                    "grass" -> R.color.grass_color
                    "ice" -> R.color.ice_color
                    "fighting" -> R.color.fighting_color
                    "poison" -> R.color.poison_color
                    "ground" -> R.color.ground_color
                    "flying" -> R.color.flying_color
                    "psychic" -> R.color.psychic_color
                    "bug" -> R.color.bug_color
                    "rock" -> R.color.rock_color
                    "ghost" -> R.color.ghost_color
                    "dragon" -> R.color.dragon_color
                    "dark" -> R.color.dark_color
                    "steel" -> R.color.steel_color
                    "fairy" -> R.color.fairy_color
                    "stellar" -> R.color.stellar_color
                    else -> R.color.default_color
                }

                typeForm.setBackgroundColor(ContextCompat.getColor(root.context, colorResId))
                typeForm.text = type.type.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<TypesItem>() {
        override fun areItemsTheSame(oldItem: TypesItem, newItem: TypesItem): Boolean {
            return oldItem.type == newItem.type

        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: TypesItem, newItem: TypesItem): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypesViewHolder {
        return TypesViewHolder(
            TypeItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: TypesViewHolder, position: Int) {
        val type= differ.currentList[position]
        holder.bind(type)



    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}