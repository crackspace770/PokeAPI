package com.fajar.pokeapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.fajar.core.data.Resource
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.domain.usecase.PokemonUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase): ViewModel() {

    private val selectedPokemon = MutableLiveData<Pokemon>()


    fun setSelectedPokemon(pokemon: Pokemon) {
        this.selectedPokemon.value = pokemon
    }

    var pokesDetail: LiveData<Resource<Pokemon>> =
        selectedPokemon.switchMap { pokemon ->
            pokemonUseCase.getDetailPokemon(pokemon).asLiveData()
        }

    fun setFavoritePokemon(item:Pokemon, newState:Boolean) {
        viewModelScope.launch {
            pokemonUseCase.setFavoritePokemon(item,newState)
        }
    }


}