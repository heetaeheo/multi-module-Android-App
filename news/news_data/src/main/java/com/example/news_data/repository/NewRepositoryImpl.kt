package com.example.news_data.repository

import com.example.news_data.network.NewsApiService
import com.example.news_domain.model.Article
import com.example.news_domain.repository.NewsRepository
import toDomainArticle
import javax.inject.Inject

class NewRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService
): NewsRepository {
    override suspend fun getNewsArticle(): List<Article> {
        return newsApiService.getNewsArticles().articleDTO.map { it.toDomainArticle() }
    }
}