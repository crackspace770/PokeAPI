package com.fajar.pokeapi.ui.favorite

import androidx.lifecycle.ViewModel
import com.fajar.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor (private val pokemonUseCase: PokemonUseCase) :ViewModel() {



}