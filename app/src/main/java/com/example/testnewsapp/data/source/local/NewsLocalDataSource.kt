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

    /**
     * Fetch all articles from the local data source.
     *
     * @return result with respect to list of article.
     */
    suspend fun getArticles(): Result<List<Article>>

    /**
     * Fetch one article from the local data source.
     *
     * @return result with respect to one article.
     */
    suspend fun getArticle(articleId: Int): Result<Article>

    /**
     * Observe the changes in the list of articles from local data sources.
     *
     * @return result with respect to changes in the list of articles.
     */
    fun observeArticles(): LiveData<Result<List<Article>>>

    /**
     * Observe the changes in one article from local data sources.
     *
     * @return result with respect to changes in one article.
     */
    fun observeArticle(articleId: Int): LiveData<Result<Article>>

    /**
     * Observe the changes in the number of comments from local data sources.
     *
     * @return result with respect to changes in the number of comments.
     */
    fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>>

    /**
     * Observe the changes in the number of likes from local data sources.
     *
     * @return result with respect to changes in the number of likes.
     */
    fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>>

    /**
     * Clear the existing list of articles and insert new articles.
     */
    suspend fun clearAndCacheArticles(articles: List<Article>)

    /**
     * Clear the existing number of likes and insert new number of likes.
     */
    suspend fun insertLikes(likes: Likes)

    /**
     * Clear the existing number of comments and insert new number of comments.
     */
    suspend fun insertComments(comments: Comments)

}