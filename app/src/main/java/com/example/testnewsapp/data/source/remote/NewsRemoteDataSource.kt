package com.example.testnewsapp.data.source.remote

import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes
import com.example.testnewsapp.data.source.remote.HeadlineResponse
import retrofit2.Response

/**
 * @author Abhradeep Ghosh
 */
interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(): Response<HeadlineResponse>

    suspend fun getNumberOfLikes(url: String): Response<ArticleLikes>

    suspend fun getNumberOfComments(url: String): Response<ArticleComments>

}