package com.fajar.pokeapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fajar.pokeapi.core.data.remote.network.ApiConfig
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _pokeDetail = MutableLiveData<PokemonDetailResponse?>()
    val pokeDetail: LiveData<PokemonDetailResponse?> = _pokeDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    // Function to fetch the detail of a specific surah
    fun fetchPokemonDetail(name: String) {
        _isLoading.value = true

        val client = ApiConfig.provideApiService().getPokeDetail(name)
        client.enqueue(object : Callback<PokemonDetailResponse> {
            override fun onResponse(call: Call<PokemonDetailResponse>, response: Response<PokemonDetailResponse>) {
                if (response.isSuccessful) {
                    val detailResponse = response.body()
                    if (detailResponse != null) {
                        _pokeDetail.value = detailResponse
                    } else {
                        _snackbarText.value = "Pokemon response is null"
                    }
                } else {
                    _snackbarText.value = "Failed to fetch Surah detail"
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                _snackbarText.value = "Failed to fetch Pokemon detail: ${t.message}"
                _isLoading.value = false
            }
        })
    }

}