package com.example.testnewsapp.data.source.remote

import com.example.testnewsapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Abhradeep Ghosh
 */
interface NewsApi {

    @GET("top-headlines?country=us&category=business&apiKey=${BuildConfig.NEWS_API_KEY}")
    suspend fun getTopHeadlines(): Response<HeadlineResponse>
}