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

    /**
     * Fetch all articles from the local data source.
     *
     * @return list of article.
     */
    override suspend fun getArticles(): Result<List<Article>> {
        return Result.Success(dao.getArticles())
    }

    /**
     * Fetch one article from the local data source.
     *
     * @return one article.
     */
    override suspend fun getArticle(articleId: Int): Result<Article> {
        val article = dao.getArticleById(articleId)
        article?.let {
            return Result.Success(it)
        }
        return Result.Error("Article not found!")
    }

    /**
     * Observe the changes in the list of articles from local data sources.
     *
     * @return changes in the list of articles.
     */
    override fun observeArticles(): LiveData<Result<List<Article>>> {
        return dao.observeArticles().map { Result.Success(it) }
    }

    /**
     * Observe the changes in one article from local data sources.
     *
     * @return changes in one article.
     */
    override fun observeArticle(articleId: Int): LiveData<Result<Article>> {
        return dao.observeArticleById(articleId).map { Result.Success(it) }
    }

    /**
     * Observe the changes in the number of comments from local data sources.
     *
     * @return changes in the number of comments.
     */
    override fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>> {
        return dao.observeArticleNumberOfComments(articleId).map { Result.Success(it) }
    }

    /**
     * Observe the changes in the number of likes from local data sources.
     *
     * @return changes in the number of likes.
     */
    override fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>> {
        return dao.observeArticleNumberOfLikes(articleId).map { Result.Success(it) }
    }

    /**
     * Clear the existing list of articles and insert new articles.
     */
    override suspend fun clearAndCacheArticles(articles: List<Article>) {
        dao.clearAndCacheArticles(articles)
    }

    /**
     * Clear the existing number of likes and insert new number of likes.
     */
    override suspend fun clearAndCacheLikes(likes: Likes) {
        dao.clearAndCacheLikes(likes)
    }

    /**
     * Clear the existing number of comments and insert new number of comments.
     */
    override suspend fun clearAndCacheComments(comments: Comments) {
        dao.clearAndCacheComments(comments)
    }
}