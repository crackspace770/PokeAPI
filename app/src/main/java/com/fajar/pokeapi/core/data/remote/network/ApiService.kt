package com.fajar.pokeapi.core.data.remote.network

import com.fajar.pokeapi.core.data.remote.response.ListPokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.ListPokemonResponse
import com.fajar.pokeapi.core.data.remote.response.ListPokemonsResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET("pokemon")
    fun getPokemonList(): Call<ListPokemonResponse>

    @GET("pokemon")
    fun getsPokemonList(): Call<ListPokemonsResponse>

    @GET("pokemon")
    fun getSearch(
        @Query("pokemon") name: String
    ): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokeDetail(
        @Path("name") name: String,
    ): Call<PokemonDetailResponse>

}