package com.example.testnewsapp.data.source.remote

import retrofit2.Response

/**
 * @author Abhradeep Ghosh
 */
interface NewsRemoteDataSource {

    /**
     * Fetch top headlines ( articles ) from remote data source
     *
     * @return response containing list of top headlines ( articles ).
     */
    suspend fun getTopHeadlines(): Response<HeadlineResponse>

    /**
     * Fetch number of likes for a particular article from remote data source
     *
     * @return response containing number of likes.
     */
    suspend fun getNumberOfLikes(url: String): Response<ArticleLikes>

    /**
     * Fetch number of comments for a particular article from remote data source
     *
     * @return response containing number of comments.
     */
    suspend fun getNumberOfComments(url: String): Response<ArticleComments>

}