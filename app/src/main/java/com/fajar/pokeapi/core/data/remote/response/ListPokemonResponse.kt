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

	val name: String,
	val url: String
)
