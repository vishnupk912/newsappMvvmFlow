package com.test.newsapp.data.model

data class HeadlinesResponseModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)