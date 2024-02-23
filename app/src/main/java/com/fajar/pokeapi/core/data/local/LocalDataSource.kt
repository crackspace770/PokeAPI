package com.fajar.pokeapi.core.data.local

import androidx.lifecycle.LiveData
import com.fajar.pokeapi.core.data.local.entity.PokemonEntity
import com.fajar.pokeapi.core.data.local.room.PokemonDao

class LocalDataSource private constructor(private val pokemonDao: PokemonDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(tourismDao: PokemonDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(tourismDao)
            }
    }

    fun getAllPokemon(): LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemon()

    fun getFavoritePokemon(): LiveData<List<PokemonEntity>> = pokemonDao.getFavoritePokemon()

    fun insertPokemon(pokemonList: List<PokemonEntity>) = pokemonDao.insertPokemon(pokemonList)

    fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        pokemon.isFavorite = newState
        pokemonDao.updateFavoritePokemon(pokemon)
    }

}