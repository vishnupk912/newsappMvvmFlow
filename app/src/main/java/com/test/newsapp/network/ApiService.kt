package com.test.newsapp.network

import com.test.newsapp.data.model.HeadlinesResponseModel
import retrofit2.http.GET

interface ApiService {


    @GET("v2/top-headlines")
    suspend fun getHeadlines(): HeadlinesResponseModel

}