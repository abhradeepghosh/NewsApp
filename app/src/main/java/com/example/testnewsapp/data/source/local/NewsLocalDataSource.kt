package com.example.testnewsapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes
import com.example.testnewsapp.data.Result

/**
 * @author Abhradeep Ghosh
 */
interface NewsLocalDataSource {

    suspend fun getArticles(): Result<List<Article>>

    suspend fun getArticle(articleId: Int): Result<Article>

    suspend fun getArticleNumberOfComments(): LiveData<Result<Comments>>

    suspend fun getArticleNumberOfLikes(): LiveData<Result<List<Likes>>>

    fun observeArticles(): LiveData<Result<List<Article>>>

    fun observeArticle(articleId: Int): LiveData<Result<Article>>

    fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>>

    fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>>

    suspend fun insertArticles(articles: List<Article>): Result<List<Long>>

    suspend fun clearAndCacheArticles(articles: List<Article>)

    suspend fun clearAndCacheLikes(likes: Likes)

    suspend fun clearAndCacheComments(comments: Comments)

}