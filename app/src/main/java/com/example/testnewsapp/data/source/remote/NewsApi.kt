package com.example.testnewsapp.data.source.remote

import com.example.testnewsapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author Abhradeep Ghosh
 */

interface NewsApi {

    /**
     * Fetch top headlines ( articles ) from remote data source
     *
     * @return response containing list of top headlines ( articles ).
     */
    @GET("top-headlines?country=us&apiKey=${BuildConfig.NEWS_API_KEY}")
    suspend fun getTopHeadlines(): Response<HeadlineResponse>

    /**
     * Fetch number of likes for a particular article from remote data source
     *
     * @return response containing number of likes.
     */
    @GET()
    suspend fun getNumberOfLikes(@Url url: String): Response<ArticleLikes>

    /**
     * Fetch number of comments for a particular article from remote data source
     *
     * @return response containing number of comments.
     */
    @GET()
    suspend fun getNumberOfComments(@Url url: String): Response<ArticleComments>
}