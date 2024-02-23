package com.fajar.pokeapi.core.domain.repository

import androidx.lifecycle.LiveData
import com.fajar.pokeapi.core.data.Resource
import com.fajar.pokeapi.core.domain.model.Pokemon

interface IPokemonRepository {

    fun getAllPokemon(): LiveData<Resource<List<Pokemon>>>

    fun getDetailPokemon(name:Pokemon) : LiveData<Resource<Pokemon>>

    fun getFavoritePokemon(): LiveData<List<Pokemon>>

    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)

}