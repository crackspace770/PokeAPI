package com.fajar.pokeapi.core.domain.usecase

import androidx.lifecycle.LiveData
import com.fajar.pokeapi.core.data.Resource
import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.domain.repository.IPokemonRepository

class PokemonInteractor(private val pokemonRepository: IPokemonRepository): PokemonUseCase  {


    override fun getAllPokemon() = pokemonRepository.getAllPokemon()

    override fun getSearchPokemon(name:String) = pokemonRepository.getSearchPokemon(name)

    override fun getDetailPokemon(pokemon: Pokemon) = pokemonRepository.getDetailPokemon(pokemon)

    override fun getFavoritePokemon() = pokemonRepository.getFavoritePokemon()

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) = pokemonRepository.setFavoritePokemon(pokemon, state)
}