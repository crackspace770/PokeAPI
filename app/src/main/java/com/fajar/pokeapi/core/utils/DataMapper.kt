package com.fajar.pokeapi.core.utils

import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.data.local.entity.PokemonEntity
import com.fajar.pokeapi.core.data.remote.response.ListPokemonResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import com.fajar.pokeapi.core.data.remote.response.StatsItem

object DataMapper {


    fun mapResponsesToEntities(input: ListPokemonResponse): List<PokemonEntity> {
        val pokeList = ArrayList<PokemonEntity>()
        input.results.forEach {

            val pokemon = PokemonEntity(
                null,
                it.name,
                it.url,
                null,
                null,
                null,
                null,
                null

            )
            pokeList.add(pokemon)

        }
        return pokeList

    }



    fun mapEntitiesToDomain(input: List<PokemonEntity>): List<Pokemon> =
        input.let { list ->
            list.map {
                Pokemon(
                    id = it.id,
                    name = it.name,
                    types = it.types,
                    height = it.height,
                    weight = it.weight,
                    abilities = it.abilities,
                    stats = it.stats,
                    sprites = it.sprites,
                    isFavorite = it.isFavorite,
                )
            }
        }

    fun mapDetailPokemonResponseToDomain(data: PokemonDetailResponse): Pokemon {
        data.apply {
            val typeList = ArrayList<String>()
            types.forEach { typeList.add(it.type.name) }
            val types = typeList.joinToString(separator = ", ")

            val abilityList = ArrayList<String>()
            abilities.forEach { abilityList.add(it.ability.name) }
            val ability = abilityList.joinToString (separator = ", ")

            // Map the base stats correctly
            val statsList = ArrayList<String>()
            stats.forEach { statsList.add("${it.stat.name}: ${it.baseStat}") }
            val stats = statsList.joinToString(separator = ", ")

            return Pokemon(
                id,
                name,
                types,
                height,
                weight,
                ability,
                stats,
                sprites.frontDefault,

            )
        }
    }

    fun mapSearchResponseDomain(data:ListPokemonResponse): List<Pokemon> {
        return data.results.map{
            Pokemon(
                null,
                it.name,
                it.url,
                null,
                null,
                null,
                null,
                null
            )
        }

    }

    fun mapDomainToEntity(input: Pokemon) = PokemonEntity(
        id = input.id,
        name = input.name,
        types = input.types,
        height = input.height,
        weight = input.weight,
        abilities = input.abilities,
        stats = input.stats,
        sprites = input.sprites,
        isFavorite = input.isFavorite,
    )


}