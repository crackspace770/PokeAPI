package com.fajar.pokeapi.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.fajar.pokeapi.core.data.Resource
import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.domain.usecase.PokemonUseCase

class SearchViewModel(private val pokemonUseCase: PokemonUseCase):ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    var searchResult: LiveData<Resource<List<Pokemon>>> =
        searchQuery.switchMap { name ->
            pokemonUseCase.getSearchPokemon(name).asLiveData()
        }


}