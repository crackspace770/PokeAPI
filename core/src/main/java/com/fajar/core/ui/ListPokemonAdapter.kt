package com.fajar.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.fajar.core.R
import com.fajar.core.databinding.PokemonsItemsBinding
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.utils.Constant.Companion.OFFICIAL_ARTWORK_URL


class ListPokemonAdapter : RecyclerView.Adapter<ListPokemonAdapter.ListViewHolder>() {

    var onItemClick: ((Pokemon) -> Unit)? = null

    private var listData = ArrayList<Pokemon>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Pokemon>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonsItemsBinding.bind(itemView)
        fun bind(data: Pokemon) {

            val officialArtworkUrl = "${OFFICIAL_ARTWORK_URL}${position + 1}.png"

            binding.composeView.setContent {
                MaterialTheme {
                    data.name?.let {
                        PokeItem(
                            imgPokemon = officialArtworkUrl,
                            tvPokemon = it,
                            onItemClick = { onItemClick?.invoke(data) }
                        )
                    }
                }
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun PokeItem (
    imgPokemon:String?,
    tvPokemon:String,
    onItemClick: () -> Unit
){
    ConstraintLayout(modifier = Modifier.clickable { onItemClick() }) {
    val (imagePokemon, pokeName) = createRefs()

        AsyncImage(
            model = imgPokemon,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(imagePokemon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }
            )

        Text(
            text = tvPokemon,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(pokeName) {
                    top.linkTo(imagePokemon.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                }

        )

    }

}

@Preview(showBackground = true)
@Composable
fun PokeItemPreview() {
    MaterialTheme {
        PokeItem(imgPokemon = "", tvPokemon = "Bulbasaur") {

        }
    }
}