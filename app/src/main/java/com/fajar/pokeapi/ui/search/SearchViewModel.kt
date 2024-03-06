package com.fajar.pokeapi.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.fajar.core.data.Resource
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase):ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    var searchResult: LiveData<Resource<List<Pokemon>>> =
        searchQuery.switchMap { name ->
            pokemonUseCase.getSearchPokemon(name).asLiveData()
        }


}