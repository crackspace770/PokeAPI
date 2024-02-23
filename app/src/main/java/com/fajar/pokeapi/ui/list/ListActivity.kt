package com.fajar.pokeapi.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.pokeapi.core.ui.ListPokemonAdapter
import com.fajar.pokeapi.core.ui.ListPokemonAdapters
import com.fajar.pokeapi.databinding.ActivityListBinding
import com.fajar.pokeapi.ui.detail.DetailActivity

class ListActivity:AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel: ListViewModel by viewModels()
    private val pokemonAdapter = ListPokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView for VerseItems
        val pokemonAdapter = ListPokemonAdapters()
        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        binding.rvPokemon.adapter = pokemonAdapter

        viewModel.listPokemon.observe(this){ pokemon->
            pokemonAdapter.submitList(pokemon)

        }

        viewModel.isLoading.observe(this){isLoading->
            showLoading(isLoading)
        }

        pokemonAdapter.onItemClick = { pokemon ->
            // Handle item click here
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("name", pokemon.name) // Pass the surah number
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading:Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

}