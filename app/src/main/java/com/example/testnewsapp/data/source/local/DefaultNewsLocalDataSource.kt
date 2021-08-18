package com.example.testnewsapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes
import com.example.testnewsapp.data.Result
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */
class DefaultNewsLocalDataSource @Inject constructor(private val dao: NewsDao) :
    NewsLocalDataSource {

    override suspend fun getArticles(): Result<List<Article>> {
        return Result.Success(dao.getArticles())
    }

    override suspend fun getArticle(articleId: Int): Result<Article> {
        val article = dao.getArticleById(articleId)
        article?.let {
            return Result.Success(article)
        }
        return Result.Error("Article not found!")
    }

    override suspend fun getArticleNumberOfComments(): LiveData<Result<Comments>> {
        return dao.getArticleNumberOfComments().map {  Result.Success(it) }
    }

    override suspend fun getArticleNumberOfLikes(): LiveData<Result<List<Likes>>> {
        return dao.getArticleNumberOfLikes().map {  Result.Success(it) }
    }

    override fun observeArticles(): LiveData<Result<List<Article>>> {
        return dao.observeArticles().map { Result.Success(it) }
    }

    override fun observeArticle(articleId: Int): LiveData<Result<Article>> {
        return dao.observeArticleById(articleId).map {  Result.Success(it) }
    }

    override fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>> {
        return dao.observeArticleNumberOfComments(articleId).map { Result.Success(it) }
    }

    override fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>> {
        return dao.observeArticleNumberOfLikes(articleId).map { Result.Success(it) }
    }

    override suspend fun insertArticles(articles: List<Article>): Result<List<Long>> {
        return Result.Success(dao.insertArticles(articles))
    }

    override suspend fun clearAndCacheArticles(articles: List<Article>) {
        dao.clearAndCacheArticles(articles)
    }

    override suspend fun clearAndCacheLikes(likes: Likes) {
        dao.clearAndCacheLikes(likes)
    }

    override suspend fun clearAndCacheComments(comments: Comments) {
        dao.clearAndCacheComments(comments)
    }
}