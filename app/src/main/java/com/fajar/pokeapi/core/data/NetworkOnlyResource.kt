package com.fajar.pokeapi.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.fajar.pokeapi.core.data.remote.network.ApiResponse
import com.fajar.pokeapi.core.utils.AppExecutors


abstract class NetworkOnlyResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {


    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.Loading()
        val apiResponseLiveData = createCall()

        result.addSource(apiResponseLiveData) { apiResponse ->
            result.removeSource(apiResponseLiveData)

            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    val networkResultLiveData = loadFromNetwork(data)
                    result.addSource(networkResultLiveData) { resultType ->
                        result.value = Resource.Success(resultType)
                    }
                }

                is ApiResponse.Error -> {
                    result.value = Resource.Error(apiResponse.errorMessage)
                    onFetchFailed()
                }
                else -> {}
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun loadFromNetwork(data: RequestType): LiveData<ResultType>

    fun asLiveData() = result as LiveData<Resource<ResultType>>
}