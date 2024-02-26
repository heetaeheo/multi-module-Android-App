package com.example.news_domain.use_case

import android.util.Log
import com.example.common_utils.Resource
import com.example.news_domain.model.Article
import com.example.news_domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsArticleUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        runCatching {
            newsRepository.getNewsArticle()
        }.onSuccess {
            Log.d("HHT","onSuccess : $it")
            emit(Resource.Success(data = it))
        }.onFailure {
            Log.d("HHT","onFailure : $it")
            emit(Resource.Error(message = it.message.toString()))
        }
    }

}