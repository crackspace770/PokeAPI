package com.fajar.pokeapi.core.domain.usecase

import androidx.lifecycle.LiveData
import com.fajar.pokeapi.core.data.Resource
import com.fajar.pokeapi.core.domain.model.Pokemon

interface PokemonUseCase {

    fun getAllPokemon(): LiveData<Resource<List<Pokemon>>>
    fun getDetailPokemon(pokemon:Pokemon): LiveData<Resource<Pokemon>>
    fun getFavoritePokemon(): LiveData<List<Pokemon>>
    fun setFavoritePokemon(tourism: Pokemon, state: Boolean)

}