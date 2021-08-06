package com.example.testnewsapp.source.remote

import com.example.testnewsapp.data.source.remote.HeadlineResponse
import com.example.testnewsapp.data.source.remote.NewsRemoteDataSource
import retrofit2.Response
import retrofit2.Response.success

/**
 * @author Abhradeep Ghosh
 */
class FakeNewsRemoteDataSource(val headlineResponse : HeadlineResponse?) : NewsRemoteDataSource {

    override suspend fun getTopHeadlines(): Response<HeadlineResponse> {
        headlineResponse?.let {
            return success(it)
        }
        return error("no response")
    }

}