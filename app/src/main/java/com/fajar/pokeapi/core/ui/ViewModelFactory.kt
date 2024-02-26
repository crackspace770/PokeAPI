package com.fajar.pokeapi.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajar.pokeapi.core.di.Injection
import com.fajar.pokeapi.core.domain.usecase.PokemonUseCase
import com.fajar.pokeapi.ui.detail.DetailViewModel
import com.fajar.pokeapi.ui.list.ListViewModel

class ViewModelFactory private constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.providePokemonUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(pokemonUseCase) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pokemonUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}