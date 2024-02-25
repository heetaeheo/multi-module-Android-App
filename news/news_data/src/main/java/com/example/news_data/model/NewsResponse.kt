package com.example.news_data.model

data class NewsResponse(
    val articleDTO: List<ArticleDTO>,
    val status: String,
    val totalResults: Int
)