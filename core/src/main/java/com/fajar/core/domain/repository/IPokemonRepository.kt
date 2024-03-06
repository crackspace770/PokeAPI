package com.fajar.core.domain.repository


import com.fajar.core.data.Resource
import com.fajar.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {

    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>

    fun getDetailPokemon(pokemon:Pokemon) : Flow<Resource<Pokemon>>

    fun getSearchPokemon(name:String) : Flow<Resource<List<Pokemon>>>

    fun getFavoritePokemon(): Flow<List<Pokemon>>

    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)

}