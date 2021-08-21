package com.example.testnewsapp.source.remote

import com.example.testnewsapp.data.source.remote.ArticleComments
import com.example.testnewsapp.data.source.remote.ArticleLikes
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

    override suspend fun getNumberOfLikes(url: String): Response<ArticleLikes> {
        TODO("Not yet implemented")
    }

    override suspend fun getNumberOfComments(url: String): Response<ArticleComments> {
        TODO("Not yet implemented")
    }

}