package com.fajar.pokeapi.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.pokeapi.R
import com.fajar.pokeapi.core.data.Resource
import com.fajar.pokeapi.core.ui.ListPokemonAdapter
import com.fajar.pokeapi.core.ui.ListPokemonAdapters
import com.fajar.pokeapi.core.ui.ViewModelFactory
import com.fajar.pokeapi.databinding.ActivityListBinding
import com.fajar.pokeapi.ui.detail.DetailActivity

class ListActivity:AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel
    //private val pokemonAdapter = ListPokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ListViewModel::class.java]

        val pokemonAdapter = ListPokemonAdapter()

        viewModel.pokemon.observe(this@ListActivity) { pokemon ->
            if (pokemon != null) {
                when (pokemon) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        pokemonAdapter.setData(pokemon.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE

                    }

                    else -> {

                    }
                }
            }
        }

        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        binding.rvPokemon.adapter = pokemonAdapter

        pokemonAdapter.onItemClick = { pokemon ->
            // Handle item click here
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_POKE, pokemon) // Pass the surah number
            startActivity(intent)
        }
    }


}