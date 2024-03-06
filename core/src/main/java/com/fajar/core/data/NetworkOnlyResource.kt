package com.fajar.core.data


import com.fajar.core.data.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


abstract class NetworkOnlyResource<ResultType, RequestType>() {
    private val result: Flow<com.fajar.core.data.Resource<ResultType>> = flow {
        emit(com.fajar.core.data.Resource.Loading())
        when (val apiResponse = createCall().first()){
            is ApiResponse.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    com.fajar.core.data.Resource.Success(
                        it
                    )
                })
            }
            is ApiResponse.Empty -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    com.fajar.core.data.Resource.Success(
                        it
                    )
                })
            }
            is ApiResponse.Error -> {
                emit(com.fajar.core.data.Resource.Error(apiResponse.errorMessage))
            }

            else -> {}
        }
    }


    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun loadFromNetwork(data: RequestType): Flow<ResultType>

    fun asFlow(): Flow<com.fajar.core.data.Resource<ResultType>> = result
}