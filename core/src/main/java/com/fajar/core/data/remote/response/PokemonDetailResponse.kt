package com.fajar.core.data.remote.response

import com.google.gson.annotations.SerializedName



data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val types: List<TypesItem>,
    val height: Int,
    val weight: Int,
    val abilities: List<AbilitiesItem>,
    val stats: List<StatsItem>,
    val sprites: Sprites
)

data class TypesItem(
	val slot: Int,
	val type: Type
)

data class AbilitiesItem(
    val isHidden: Boolean,
    val ability: Ability,
    val slot: Int
)

data class StatsItem(
	val stat: Stat,
	@SerializedName("base_stat")
	val baseStat: Int,
	val effort: Int
)

data class Type(
	val name: String,
	val url: String
)

data class Ability(
	val name: String,
	val url: String
)

data class Stat(
	val name: String,
	val url: String
)

data class Sprites(
	val frontDefault: String,
	val frontShiny: String,
	val frontFemale: Any?,
	val frontShinyFemale: Any?,
	val backDefault: String,
	val backShiny: String,
	val backFemale: Any?,
	val backShinyFemale: Any?,
	val other: Other
)

data class Other(
	val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
	val frontDefault: String,
	val frontShiny: String
)