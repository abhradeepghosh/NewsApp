package com.example.testnewsapp.data.source.remote

import retrofit2.Response
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */
class DefaultNewsRemoteDataSource @Inject constructor(private val api: NewsApi) :
    NewsRemoteDataSource {

    override suspend fun getTopHeadlines(): Response<HeadlineResponse> {
        return api.getTopHeadlines()
    }
}