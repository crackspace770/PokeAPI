package com.fajar.core.data.remote.network



import com.fajar.core.data.remote.response.ListPokemonResponse
import com.fajar.core.data.remote.response.ListPokemonsResponse
import com.fajar.core.data.remote.response.PokemonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET("pokemon")
    suspend fun getPokemonList(): ListPokemonResponse

    @GET("pokemon")
    suspend fun getsPokemonList(): ListPokemonsResponse

    @GET("pokemon")
    suspend fun getSearch(
        @Query("pokemon") name: String
    ): ListPokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokeDetail(
        @Path("name") name: String,
    ): PokemonDetailResponse

}