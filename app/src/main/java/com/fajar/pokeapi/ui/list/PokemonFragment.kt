package com.fajar.pokeapi.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.fajar.pokeapi.core.ui.ListPokemonAdapter
import com.fajar.pokeapi.core.ui.ListPokemonAdapters
import com.fajar.pokeapi.databinding.ActivityListBinding
import com.fajar.pokeapi.databinding.FragmentPokemonBinding
import com.fajar.pokeapi.ui.detail.DetailActivity

class PokemonFragment: Fragment() {

    private var binding: FragmentPokemonBinding? = null
    private val viewModel: ListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView for VerseItems
        val pokemonAdapter = ListPokemonAdapters()
        binding?.rvPokemon?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.rvPokemon?.adapter = pokemonAdapter

        viewModel.listPokemon.observe(requireActivity()){ pokemon->
            pokemonAdapter.submitList(pokemon)

        }

        viewModel.isLoading.observe(requireActivity()){isLoading->
            showLoading(isLoading)
        }

        pokemonAdapter.onItemClick = { pokemon ->
            // Handle item click here
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("name", pokemon.name) // Pass the surah number
            startActivity(intent)
        }

    }

    private fun showLoading(isLoading:Boolean){
        binding?.progressBar?.visibility = if(isLoading) View.VISIBLE else View.GONE
    }


}