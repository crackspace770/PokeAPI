package com.fajar.core.di


import com.fajar.core.domain.usecase.PokemonInteractor
import com.fajar.core.domain.usecase.PokemonUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun providePokemonUseCase(pokemonInteractor: PokemonInteractor): PokemonUseCase

}