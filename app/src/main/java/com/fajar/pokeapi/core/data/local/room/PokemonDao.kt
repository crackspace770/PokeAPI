package com.fajar.pokeapi.core.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fajar.pokeapi.core.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon where isFavorite = 1")
    fun getFavoritePokemon(): LiveData<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(tourism: List<PokemonEntity>)

    @Update
    fun updateFavoritePokemon(tourism: PokemonEntity)

}