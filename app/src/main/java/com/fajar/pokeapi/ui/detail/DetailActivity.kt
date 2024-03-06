package com.fajar.pokeapi.ui.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.core.data.Resource
import com.fajar.core.data.remote.response.Stat
import com.fajar.core.data.remote.response.StatsItem
import com.fajar.core.data.remote.response.Type
import com.fajar.core.data.remote.response.TypesItem
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.ui.StatsAdapter
import com.fajar.core.ui.TypeAdapter
import com.fajar.core.utils.Constant
import com.fajar.core.utils.HorizontalSpaceItemDecoration
import com.fajar.core.utils.Utils.loadImageUrl
import com.fajar.pokeapi.R
import com.fajar.pokeapi.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var pokemonTitle: String
    private val typeAdapter by lazy { TypeAdapter() }
    private val statAdapter by lazy { StatsAdapter() }
    private val viewModel: DetailViewModel by viewModels ()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(findViewById(R.id.my_toolbar))

        binding.imgArrow.setOnClickListener {
           onBackPressed()
        }

        val pokemon = intent.getParcelableExtra<Pokemon>(EXTRA_POKE)


        getPokemonDetail(pokemon)
        setupRvStats()
        setupRvType()
    }

    private fun getPokemonDetail(pokemon:Pokemon?) {

        binding.apply {
            pokemon?.let { it ->

                pokemonTitle = it.name ?: getString(R.string.no_data)
                showDetailPokemon(it)
            }
            progressBar.visibility = View.VISIBLE
            viewError.root.visibility = View.GONE
            pokemon?.let { viewModel.setSelectedPokemon(it) }
            viewModel.pokesDetail.observe(this@DetailActivity) { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            progressBar.visibility = View.GONE
                            movie.data?.let {
                                showDetailPokemon(it)
                            }
                        }
                        is Resource.Error -> {
                            progressBar.visibility = View.GONE
                            viewError.root.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showDetailPokemon(pokemon: Pokemon?){

        binding.apply {
            pokemon?.apply {

                val heightInMeters = height?.toFloat()?.div(10)
                val formattedHeight = "%.1f".format(heightInMeters)

                val weightInKg = weight?.toFloat()?.div(10)
                val formattedWeight = "%.1f".format(weightInKg)

                tvPokemonName.text = name
                val formattedId = String.format("#%03d", id)
                tvId.text = formattedId

                tvHeight.text = "${formattedHeight}m"
                tvWeight.text = "${formattedWeight}kg"

                // Construct the URL for the official artwork image
                val officialArtworkUrl = "${Constant.OFFICIAL_ARTWORK_URL}${id}.png"

             
                imgPokemon.loadImageUrl(officialArtworkUrl)


                val typesList = types?.let { parseTypesString(it) }

                typesList?.let { typeAdapter.differ.submitList(it) }

                val statsList = stats?.let { parseStatsString(it) }

                statsList?.let { statAdapter.differ.submitList(it) }

                typesList?.firstOrNull()?.type?.name?.let { typeName ->
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
                        "stellar" -> R.color.stellar_color
                        // Add more cases for other types
                        else -> R.color.default_color
                    }

                    window.statusBarColor = ContextCompat.getColor(root.context, colorResId)
                    backgroundColor.setBackgroundResource(colorResId)
                    cardView.setCardBackgroundColor(ContextCompat.getColor(root.context, colorResId))

                }

            }
            val horizontalSpacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing) // Define your desired spacing dimension
            val itemDecoration = HorizontalSpaceItemDecoration(horizontalSpacingInPixels)
            rvType.addItemDecoration(itemDecoration)
        }

    }


    private fun parseTypesString(typesString: String): List<TypesItem> {
        val typesList = mutableListOf<TypesItem>()
        // Split the typesString by comma to get individual type names
        val typeNames = typesString.split(",")
        // Create a TypesItem for each type name
        typeNames.forEachIndexed { index, typeName ->
            val typeItem = TypesItem(index, Type(typeName.trim(), ""))
            typesList.add(typeItem)
        }
        return typesList
    }

    private fun parseStatsString(statsString: String): List<StatsItem> {
        val statsList = mutableListOf<StatsItem>()
        // Split the statsString by comma to get individual stat name and base stat pairs
        val statPairs = statsString.split(",")
        // Iterate through each stat pair
        statPairs.forEach { statPair ->
            // Split the stat pair by colon to get stat name and base stat value
            val parts = statPair.split(":")
            // Ensure there are two parts (name and value)
            if (parts.size == 2) {
                val statName = parts[0].trim()
                val baseStat = parts[1].trim().toIntOrNull() ?: 0 // Parse base stat value, default to 0 if parsing fails
                // Create a StatsItem for each stat name with parsed base stat value
                val statItem = StatsItem(Stat(statName, ""), baseStat, 0)
                statsList.add(statItem)
            }
        }
        return statsList
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

    companion object{
        const val EXTRA_POKE = "extra_poke"
    }

}







