package com.test.newsapp.data.repository

import com.test.newsapp.data.model.HeadlinesResponseModel
import com.test.newsapp.network.ApiService
import com.test.newsapp.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject


class NewsAppRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getHeadlines(): Flow<ApiState<HeadlinesResponseModel>> = flow {
        emit(ApiState.Loading)
        try {
            val data = apiService.getHeadlines()
            emit(ApiState.Success(data))

        } catch (e: HttpException) {
            emit(ApiState.Error(e.message()))
        }
    }.flowOn(Dispatchers.IO)

}