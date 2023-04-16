package com.test.newsapp.network


sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val exception: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

    object Empty : ApiState<Nothing>()

}
