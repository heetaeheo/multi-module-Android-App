package com.example.news_data.repository

import com.example.news_data.network.NewsApiService
import com.example.news_data.room.NewsDAO
import com.example.news_domain.model.Article
import com.example.news_domain.repository.NewsRepository
import toDomainArticle
import javax.inject.Inject

class NewRepositoryImpl(
    private val newsApiService: NewsApiService,
    private val newsDAO: NewsDAO
) : NewsRepository {
    override suspend fun getNewsArticle(): List<Article> {
        return try {
            val temp = newsApiService.getNewsArticles(country = "us").articleDTO.map { it.toDomainArticle() }
            newsDAO.insertList(temp)
            newsDAO.getNewsArticle()
        } catch (e: Exception){
            newsDAO.getNewsArticle()
        }
    }
}