package com.fajar.pokeapi.core.di

import android.content.Context
import androidx.room.Room
import com.fajar.pokeapi.core.data.local.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.fajar.pokeapi.core.data.local.room.PokemonDao
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase = Room.databaseBuilder(
        context,
        PokemonDatabase::class.java, "Pokemon.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()

}