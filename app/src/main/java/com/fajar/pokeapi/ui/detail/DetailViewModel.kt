package com.fajar.pokeapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.fajar.pokeapi.core.data.Resource
import com.fajar.pokeapi.core.data.remote.network.ApiConfig
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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


}