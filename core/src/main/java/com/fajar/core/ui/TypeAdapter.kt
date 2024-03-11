package com.fajar.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fajar.core.R
import com.fajar.core.data.remote.response.Type
import com.fajar.core.data.remote.response.TypesItem
import com.fajar.core.databinding.TypeItemBinding
import com.fajar.core.utils.PokemonTypeUtils


class TypeAdapter: RecyclerView.Adapter<TypeAdapter.TypesViewHolder>() {

    inner class TypesViewHolder(private val binding: TypeItemBinding) : ViewHolder(binding.root) {
        fun bind(type: TypesItem){
            val colorResId = PokemonTypeUtils.getTypeColor(type.type.name)
            val color = ContextCompat.getColor(itemView.context, colorResId)


            binding.composeView.setContent {
                MaterialTheme {
                    type.type.name.let {
                        TypeItem(
                            typeName = it,
                            backgroundColor = color
                        )
                    }
                }
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


@SuppressLint("SuspiciousIndentation")
@Composable
fun TypeItem(
    typeName:String,
    backgroundColor: Int,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card (
            modifier = modifier
                .width(110.dp)
                .height(40.dp),
            shape = RoundedCornerShape(8.dp),
            CardDefaults.cardColors(
                containerColor = Color(backgroundColor)
            )
        ) {

            Column(modifier= Modifier.padding(8.dp)) {
                Text(
                    text = typeName,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.White // Use the color directly here
                )


            }


        }
    }



}

