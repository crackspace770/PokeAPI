package com.fajar.pokeapi.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.fajar.core.data.Resource
import com.fajar.core.ui.ListPokemonAdapter
import com.fajar.core.utils.VerticalSpaceItemDecoration
import com.fajar.pokeapi.R
import com.fajar.pokeapi.databinding.FragmentPokemonBinding
import com.fajar.pokeapi.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFragment: Fragment() {


    private lateinit var binding: FragmentPokemonBinding
    private val viewModel: ListViewModel by viewModels ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the hosting activity and cast it to AppCompatActivity
        val activity = requireActivity() as AppCompatActivity

        // Set the toolbar as the action bar using setSupportActionBar
        activity.setSupportActionBar(binding.myToolbar)

      //  val factory = ViewModelFactory.getInstance(requireContext())
       // viewModel = ViewModelProvider(this, )[ListViewModel::class.java]

        val pokemonAdapter = ListPokemonAdapter()

        viewModel.getAllPokemon().observe(requireActivity()) { pokemon ->
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

                }
            }
        }

        binding.rvPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPokemon.adapter = pokemonAdapter

        val verticalSpacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing) // Define your desired spacing dimension
        val itemDecoration = VerticalSpaceItemDecoration(verticalSpacingInPixels)
        binding.rvPokemon.addItemDecoration(itemDecoration)

        pokemonAdapter.onItemClick = { pokemon ->
            // Handle item click here
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_POKE, pokemon)
            startActivity(intent)
        }

    }

}