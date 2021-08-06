package com.example.testnewsapp.data.source.remote

import com.example.testnewsapp.data.source.remote.HeadlineResponse
import retrofit2.Response

/**
 * @author Abhradeep Ghosh
 */
interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(): Response<HeadlineResponse>

}