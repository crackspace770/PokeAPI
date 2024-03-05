package com.fajar.pokeapi.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajar.pokeapi.core.domain.usecase.PokemonUseCase
import com.fajar.pokeapi.ui.detail.DetailViewModel
import com.fajar.pokeapi.ui.favorite.FavoriteViewModel
import com.fajar.pokeapi.ui.list.ListViewModel
import com.fajar.pokeapi.ui.search.SearchViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModelProvider.NewInstanceFactory() {

 //   companion object {
 //       @Volatile
 //       private var instance: ViewModelFactory? = null

 //       fun getInstance(context: Context): ViewModelFactory =
 //           instance ?: synchronized(this) {
 //               instance ?: ViewModelFactory(
 //                   Injection.providePokemonUseCase(context)
 //               )
 //           }
 //   }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(pokemonUseCase) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pokemonUseCase) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(pokemonUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(pokemonUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}