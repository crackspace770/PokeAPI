package com.fajar.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fajar.core.data.remote.response.StatsItem
import com.fajar.core.databinding.StatItemBinding
import com.fajar.core.utils.PokemonTypeUtils

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    inner class StatsViewHolder(private val binding: StatItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind (stat: StatsItem) {

            val colorRes = PokemonTypeUtils.getStateColor(stat.stat.name)
            val color = ContextCompat.getColor(itemView.context, colorRes)

            binding.composeView.setContent {
                MaterialTheme {
                    StatsItem(
                        statBase = stat.baseStat,
                        statsName = stat.stat.name,
                        backgroundColor = color
                    )
                }
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


}

@Composable
fun StatsItem(
    statBase: Int,
    statsName: String,
    backgroundColor: Int,
    modifier: Modifier = Modifier
) {
    val percentage = statBase / 200f

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = statsName,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "$statBase / 200",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        LinearProgressIndicator(
            progress = percentage,
            modifier = Modifier.fillMaxWidth(),
            color = Color(backgroundColor)
        )
    }
}