package com.fajar.pokeapi.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fajar.pokeapi.R

import com.fajar.pokeapi.core.data.remote.response.StatsItem
import com.fajar.pokeapi.databinding.StatItemBinding

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    inner class StatsViewHolder(private val binding:StatItemBinding):
    RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind (stat: StatsItem) {
            binding.apply {
                tvNameStat.text = stat.stat.name
                tvBaseStat.text = stat.baseStat.toString()

                tvBaseStat.text = "${stat.baseStat} / 200" // Update text to show current and max value

                // Calculate percentage of current stat value relative to maximum value
                val percentage = stat.baseStat.toFloat() / 200f
                val progress = (percentage * 100).toInt() // Calculate progress value (0-100)

                // Set progress bar color based on stat name
                val colorResId = when (stat.stat.name) {
                    "hp" -> R.color.hp_color
                    "attack" -> R.color.attack_color
                    "defense" -> R.color.defense_color
                    "special-attack" -> R.color.special_attack_color
                    "special-defense" -> R.color.special_defense_color
                    "speed" -> R.color.speed_color
                    else -> R.color.default_color
                }
                statBar.progressDrawable.setTint(ContextCompat.getColor(itemView.context, colorResId))

                // Set progress bar progress
                statBar.progress = progress

            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<StatsItem>() {
        override fun areItemsTheSame(oldItem: StatsItem, newItem: StatsItem): Boolean {
            return oldItem.stat.name == newItem.stat.name

        }

        override fun areContentsTheSame(oldItem: StatsItem, newItem: StatsItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        return StatsViewHolder(
            StatItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: StatsAdapter.StatsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

    }

    companion object {
        private const val MAX_BAR_WIDTH = 300 // Maximum width of the stat bar
    }
}