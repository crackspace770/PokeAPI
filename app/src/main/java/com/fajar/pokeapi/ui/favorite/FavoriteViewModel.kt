package com.fajar.pokeapi.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor (private val pokemonUseCase: PokemonUseCase) :ViewModel() {

    fun getFavoritePokemon() : LiveData<List<Pokemon>> {
        return pokemonUseCase.getFavoritePokemon().asLiveData()
    }
}