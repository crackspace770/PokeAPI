package com.fajar.pokeapi.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.fajar.core.data.Resource
import com.fajar.core.ui.ListPokemonAdapter
import com.fajar.pokeapi.R
import com.fajar.pokeapi.databinding.FragmentSearchBinding
import com.fajar.pokeapi.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment:Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel:SearchViewModel by viewModels()
    private val pokemonAdapter = ListPokemonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        if(activity != null) {

            binding.apply {
                rvResult.apply {
                   layoutManager = GridLayoutManager(requireContext(), 2)
                    adapter = pokemonAdapter
                }

                pokemonAdapter.onItemClick = { pokemon ->
                    // Handle item click here
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_POKE, pokemon)
                    startActivity(intent)

                }

            }
            observeSearchResult()

        }

    }

    override fun onResume() {
        super.onResume()
        if (pokemonAdapter.itemCount > 0) {
            binding.onInitialSearchStateMessage.visibility = View.GONE
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        search.expandActionView()

        val searchView = search.actionView as SearchView

        searchView.apply {
            onActionViewExpanded()
            setIconifiedByDefault(false)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            requestFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    binding.rvResult.scrollToPosition(0)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            viewError.root.visibility = View.GONE
                            viewEmpty.root.visibility = View.GONE
                            onInitialSearchStateMessage.visibility = View.GONE
                            rvResult.scrollToPosition(0)
                        }
                        viewModel.setSearchQuery(query)
                        observeSearchResult()
                    } else if (query.equals("")) {
                        pokemonAdapter.clearList()
                    }
                    return true

                }
            } )
        }

    }

    private fun observeSearchResult() {
        binding.apply {
            viewModel.searchResult.observe(viewLifecycleOwner) {results ->
                if(results != null) {
                    when(results) {
                        is Resource.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            val searchResult = results.data
                            progressBar.visibility = View.GONE
                            if (searchResult != null) {
                                pokemonAdapter.setData(searchResult)
                                rvResult.scrollToPosition(0)
                                if(searchResult.isEmpty()) {
                                    viewEmpty.root.visibility = View.VISIBLE
                                    onInitialSearchStateMessage.visibility = View.GONE

                                }
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

}