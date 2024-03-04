package com.fajar.pokeapi.core.data.local

import com.fajar.pokeapi.core.data.local.entity.PokemonEntity
import com.fajar.pokeapi.core.data.local.room.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSource private constructor(private val pokemonDao: PokemonDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(pokemonDao: PokemonDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(pokemonDao)
            }
    }

    fun getAllPokemon(): Flow<List<PokemonEntity>> = pokemonDao.getAllPokemon()

    fun getFavoritePokemon(): Flow<List<PokemonEntity>> = pokemonDao.getFavoritePokemon()

    suspend fun insertPokemon(pokemonList: List<PokemonEntity>) {
        withContext(Dispatchers.IO) {
            pokemonDao.insertPokemon(pokemonList)
        }
    }

    suspend fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        withContext(Dispatchers.IO) {
            pokemon.isFavorite = newState
            pokemonDao.updateFavoritePokemon(pokemon)
        }
    }

}