package com.fajar.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListPokemonsResponse(

    @field:SerializedName("next")
    val next: String,

    @field:SerializedName("previous")
    val previous: Any,

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("results")
    val results: List<PokemonsResponse>
)

data class PokemonsResponse (
    val name: String,
    val url: String
)