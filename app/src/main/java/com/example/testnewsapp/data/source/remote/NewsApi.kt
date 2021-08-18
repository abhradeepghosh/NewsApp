package com.example.testnewsapp.data.source.remote

import com.example.testnewsapp.BuildConfig
import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author Abhradeep Ghosh
 */
interface NewsApi {

    @GET("top-headlines?country=us&apiKey=${BuildConfig.NEWS_API_KEY}")
    suspend fun getTopHeadlines(): Response<HeadlineResponse>

    @GET()
    suspend fun getNumberOfLikes(@Url url : String) : Response<ArticleLikes>

    @GET()
    suspend fun getNumberOfComments(@Url url : String) : Response<ArticleComments>
}