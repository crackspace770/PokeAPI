package com.fajar.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajar.core.domain.usecase.PokemonUseCase

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

}