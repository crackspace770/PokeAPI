package com.fajar.pokeapi.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fajar.pokeapi.core.data.remote.network.ApiConfig
import com.fajar.pokeapi.core.data.remote.response.ListPokemonResponse
import com.fajar.pokeapi.core.data.remote.response.ListPokemonsResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel: ViewModel() {

    private val _listPokemon = MutableLiveData<List<PokemonsResponse>>()
    val listPokemon: LiveData<List<PokemonsResponse>> = _listPokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findPoke()
    }

    private fun findPoke() {
        _isLoading.value = true
        val client = ApiConfig.provideApiService().getsPokemonList()
        client.enqueue(object : Callback<ListPokemonsResponse> {
            override fun onResponse(
                call: Call<ListPokemonsResponse>,
                response: Response<ListPokemonsResponse>
            ) {
                if (response.isSuccessful) {
                    _listPokemon.value = response.body()?.results ?: emptyList()
                    Log.d(TAG, "onResponse: ${response.body()?.results}")
                } else {
                    Log.d(TAG, "onResponse: ${response.message()}")
                }
                // Update isLoading to false when the operation is complete
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ListPokemonsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                // Update isLoading to false when the operation is complete (even in case of failure)
                _isLoading.value = false
            }
        })
    }


    companion object{
        private const val TAG = "ListViewModel"
    }

}