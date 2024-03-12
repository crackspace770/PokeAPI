package com.fajar.pokeapi.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fajar.core.ui.ListPokemonAdapter
import com.fajar.pokeapi.R
import com.fajar.pokeapi.databinding.FragmentFavoriteBinding
import com.fajar.pokeapi.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment:Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private val pokemonAdapter = ListPokemonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity !=null) {

            with(binding.rvResult) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
                adapter = pokemonAdapter
            }

            pokemonAdapter.onItemClick = { pokemon ->
                // Handle item click here
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_POKE, pokemon)
                startActivity(intent)

            }

            viewModel.getFavoritePokemon().observe(viewLifecycleOwner) {pokemon->
                pokemonAdapter.setData(pokemon)

                binding.viewEmpty.root.visibility =
                    if (pokemon.isNotEmpty()) View.GONE else View.VISIBLE

            }

        }

    }

}