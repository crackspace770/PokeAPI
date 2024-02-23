package com.fajar.pokeapi.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListPokemonResponse(

	@field:SerializedName("next")
	val next: String,

	@field:SerializedName("previous")
	val previous: Any,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("results")
	val results: List<PokemonResponse>
)

data class PokemonResponse(

	@field:SerializedName("name")
	val id:Int,
	@field:SerializedName("name")
	val name: String,
	@field:SerializedName("types")
	val types: String,
	@field:SerializedName("height")
	val height: Int,
	@field:SerializedName("weight")
	val weight: Int,
	@field:SerializedName("abilities")
	val abilities: String,
	@field:SerializedName("stats")
	val stats: String,
	@field:SerializedName("sprites")
	val sprites: String
)
