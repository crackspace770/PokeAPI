package com.fajar.pokeapi.core.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fajar.pokeapi.core.data.remote.network.ApiResponse
import com.fajar.pokeapi.core.data.remote.network.ApiService
import com.fajar.pokeapi.core.data.remote.response.ListPokemonResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllPokemon(): LiveData<ApiResponse<List<PokemonResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<PokemonResponse>>>()

        //get data from remote api
        val client = apiService.getPokemonList()

        client.enqueue(object : Callback<ListPokemonResponse> {
            override fun onResponse(
                call: Call<ListPokemonResponse>,
                response: Response<ListPokemonResponse>
            ) {
                val dataArray = response.body()?.results
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListPokemonResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }

    fun getPokemonDetail(name: String): LiveData<ApiResponse<PokemonDetailResponse>> {
        val resultData = MutableLiveData<ApiResponse<PokemonDetailResponse>>()

        //get data from remote api
        val client = apiService.getPokeDetail(name)

        client.enqueue(object : Callback<PokemonDetailResponse> {
            override fun onResponse(
                call: Call<PokemonDetailResponse>,
                response: Response<PokemonDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemonDetailResponse = response.body()
                    resultData.value = if (pokemonDetailResponse != null) {
                        ApiResponse.Success(pokemonDetailResponse)
                    } else {
                        ApiResponse.Empty
                    }
                } else {
                    resultData.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }


}