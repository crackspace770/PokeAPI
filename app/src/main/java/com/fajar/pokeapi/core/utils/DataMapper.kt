package com.fajar.pokeapi.core.utils

import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.data.local.entity.PokemonEntity
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse

object DataMapper {


    fun mapResponsesToEntities(input: List<PokemonResponse> ): List<PokemonEntity> {
        val pokeList = ArrayList<PokemonEntity>()
        input.map {
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

            val statList = ArrayList<String>()
            stats.forEach { statList.add(it.stat.name) }
            val stat = abilityList.joinToString (separator = ", ")

            return Pokemon(
                id,
                name,
                types,
                height,
                weight,
                ability,
                stat,
                sprites.frontDefault,

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