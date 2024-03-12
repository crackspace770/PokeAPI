package com.fajar.core.domain.usecase

import com.fajar.core.domain.model.Pokemon
import com.fajar.core.domain.repository.IPokemonRepository

import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val pokemonRepository: IPokemonRepository): PokemonUseCase  {


    override fun getAllPokemon() = pokemonRepository.getAllPokemon()

    override fun getSearchPokemon(name:String) = pokemonRepository.getSearchPokemon(name)

    override fun getDetailPokemon(pokemon: Pokemon) = pokemonRepository.getDetailPokemon(pokemon)

    override fun getFavoritePokemon() = pokemonRepository.getFavoritePokemon()

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) = pokemonRepository.setFavoritePokemon(pokemon, state)
}