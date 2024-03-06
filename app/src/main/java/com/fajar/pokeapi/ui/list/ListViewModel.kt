package com.fajar.pokeapi.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fajar.core.data.Resource
import com.fajar.core.data.remote.response.PokemonsResponse
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val _listPokemon = MutableLiveData<List<PokemonsResponse>>()
    val listPokemon: LiveData<List<PokemonsResponse>> = _listPokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val pokemon = pokemonUseCase.getAllPokemon()

    fun getAllPokemon(): LiveData<Resource<List<Pokemon>>> {
        return pokemonUseCase.getAllPokemon().asLiveData()
    }



}