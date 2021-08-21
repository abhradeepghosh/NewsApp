package com.example.testnewsapp.data.source.remote

import retrofit2.Response
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */

private const val HEROKU_BASE_URL = "https://cn-news-info-api.herokuapp.com/"
private const val LIKES = "likes/"
private const val COMMENTS = "comments/"

class DefaultNewsRemoteDataSource @Inject constructor(private val api: NewsApi) :
    NewsRemoteDataSource {

    /**
     * Fetch top headlines ( articles ) from remote data source
     *
     * @return response containing list of top headlines ( articles ).
     */
    override suspend fun getTopHeadlines(): Response<HeadlineResponse> {
        return api.getTopHeadlines()
    }

    /**
     * Fetch number of likes for a particular article from remote data source
     *
     * @return response containing number of likes.
     */
    override suspend fun getNumberOfLikes(url: String): Response<ArticleLikes> {
        return api.getNumberOfLikes(HEROKU_BASE_URL.plus(LIKES).plus(url))
    }

    /**
     * Fetch number of comments for a particular article from remote data source
     *
     * @return response containing number of comments.
     */
    override suspend fun getNumberOfComments(url: String): Response<ArticleComments> {
        return api.getNumberOfComments(HEROKU_BASE_URL.plus(COMMENTS).plus(url))
    }

}