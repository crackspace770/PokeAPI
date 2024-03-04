package com.fajar.pokeapi.core.data.remote

import android.util.Log
import com.fajar.pokeapi.core.data.remote.network.ApiResponse
import com.fajar.pokeapi.core.data.remote.network.ApiService
import com.fajar.pokeapi.core.data.remote.response.ListPokemonResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    suspend fun getAllPokemon(): Flow<ApiResponse<ListPokemonResponse>> {
        return flow {
            try {
                val response = apiService.getPokemonList()
                val tvList = response.results
                if (tvList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Popular TvShow List")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonDetail(name: String): Flow<ApiResponse<PokemonDetailResponse>> {
        return flow {
            try {
                val response = apiService.getPokeDetail(name)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Movie Detail")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovie(name:String): Flow<ApiResponse<ListPokemonResponse>> {
        return flow {
            try {
                val response = apiService.getSearch(name)
                val searchList = response.results
                if (searchList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Search Movie Results")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


}