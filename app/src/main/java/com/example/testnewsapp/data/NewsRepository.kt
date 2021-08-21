package com.example.testnewsapp.data

import androidx.lifecycle.LiveData

/**
 * @author Abhradeep Ghosh
 */

interface NewsRepository {

    /**
     * fetch top headlines ( articles ) from remote source - api
     */
    suspend fun refreshArticles()

    /**
     * fetch top headlines ( articles ) from remote data source and store in local database
     *
     * @return top headlines ( articles ) from local data source - db
     */
    suspend fun getArticles(forceUpdate: Boolean): Result<List<Article>>

    /**
     * fetch comments from remote source - api
     */
    suspend fun getComments(article: Result<Article>)

    /**
     * fetch likes from remote source - api
     */
    suspend fun getLikes(article: Result<Article>)

    /**
     * Fetch the article from local data source.
     *
     * @return one article
     */
    suspend fun getArticle(articleId: Int): Result<Article>

    /**
     * observe the data changes for the list of top headlines ( article ) from local data source.
     *
     * @return list of top headlines ( article )
     */
    fun observeArticles(): LiveData<Result<List<Article>>>

    /**
     * observe the data changes for the article from local data source.
     *
     * @return one article
     */
    fun observeArticle(articleId: Int): LiveData<Result<Article>>

    /**
     * observe the data changes for the number of comments for a particular article from local data source.
     *
     * @return result consisting of number of comments
     */
    fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>>

    /**
     * observe the data changes for the number of likes for a particular article from local data source.
     *
     * @return result consisting of number of likes
     */
    fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>>

}