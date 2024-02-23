package com.fajar.pokeapi.core.di

import android.content.Context
import com.fajar.pokeapi.core.domain.repository.IPokemonRepository
import com.fajar.pokeapi.core.data.local.room.PokemonDatabase
import com.fajar.pokeapi.core.data.remote.RemoteDataSource
import com.fajar.pokeapi.core.data.local.LocalDataSource
import com.fajar.pokeapi.core.data.remote.network.ApiConfig
import com.fajar.pokeapi.core.utils.AppExecutors
import com.fajar.pokeapi.core.data.PokemonRepository
import com.fajar.pokeapi.core.domain.usecase.PokemonUseCase
import com.fajar.pokeapi.core.domain.usecase.PokemonInteractor

object Injection {
    private fun provideRepository(context: Context): IPokemonRepository {
        val database = PokemonDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.pokemonDao())
        val appExecutors = AppExecutors()

        return PokemonRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun providePokemonUseCase(context: Context): PokemonUseCase {
        val repository = provideRepository(context)
        return PokemonInteractor(repository)
    }
}