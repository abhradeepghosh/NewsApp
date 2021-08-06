package com.example.testnewsapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Result

/**
 * @author Abhradeep Ghosh
 */
interface NewsLocalDataSource {

    suspend fun getArticles(): Result<List<Article>>

    suspend fun getArticle(articleId: Int): Result<Article>

    fun observeArticles(): LiveData<Result<List<Article>>>

    fun observeArticle(articleId: Int): LiveData<Result<Article>>

    suspend fun insertArticles(articles: List<Article>): Result<List<Long>>

    suspend fun clearAndCacheArticles(articles: List<Article>)
}