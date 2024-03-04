package com.fajar.pokeapi.core.data.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    data class Empty<out T> (val data: T) : ApiResponse<T>()
}