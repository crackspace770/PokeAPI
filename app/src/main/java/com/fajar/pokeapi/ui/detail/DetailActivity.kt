package com.fajar.pokeapi.ui.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fajar.pokeapi.R
import com.fajar.pokeapi.core.ui.StatsAdapter
import com.fajar.pokeapi.core.ui.TypeAdapter
import com.fajar.pokeapi.databinding.ActivityDetailBinding
import com.fajar.pokeapi.core.utils.Constant

import com.fajar.pokeapi.core.utils.Utils.loadImageUrl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private val typeAdapter by lazy {TypeAdapter()}
    private val statAdapter by lazy {StatsAdapter()}

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get surahNum from intent or wherever it's coming from
        val name = intent.getStringExtra("name")

        viewModel.pokeDetail.observe(this) { pokeDetail ->

            val heightInMeters = pokeDetail?.height?.toFloat()?.div(10)
            val formattedHeight = "%.1f".format(heightInMeters)

            val weightInKg = pokeDetail?.weight?.toFloat()?.div(10)
            val formattedWeight = "%.1f".format(weightInKg)

            binding.tvPokemonName.text = pokeDetail?.name
            binding.tvHeight.text = "${formattedHeight}m"
            binding.tvWeight.text = "${formattedWeight}kg"

            // Construct the URL for the official artwork image
            val officialArtworkUrl = "${Constant.OFFICIAL_ARTWORK_URL}${pokeDetail?.id}.png"

            // Load the official artwork image
            binding.imgPokemon.loadImageUrl(officialArtworkUrl)


            if (pokeDetail != null) {
                typeAdapter.differ.submitList(pokeDetail.types)
            }
            if (pokeDetail != null) {
                statAdapter.differ.submitList(pokeDetail.stats)
            }

            pokeDetail?.types?.firstOrNull()?.type?.name?.let { typeName ->
                val colorResId = when (typeName) {
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
                    "stellar" -> R.color.steel_color
                    else -> R.color.default_color
                }
                binding.backgroundColor.setBackgroundResource(colorResId)
            }

        }

        // Implement loadImageUrl extension function for ImageView
        fun ImageView.loadImageUrl(url: String) {
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_broken_image) // Placeholder image while loading
                .error(R.drawable.ic_error_image) // Error image if loading fails
                .into(this)
        }


        if (name != null) {
            viewModel.fetchPokemonDetail(name)
        }

        setupRvStats()
        setupRvType()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupRvStats(){
        binding.rvStats.apply {
            adapter = statAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupRvType() {
        binding.rvType.apply {
            adapter = typeAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}


