package com.example.testnewsapp.data.source.remote

import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */
class DefaultNewsRemoteDataSource @Inject constructor(private val api: NewsApi) :
    NewsRemoteDataSource {

    private val HEROKU_BASE_URL = "https://cn-news-info-api.herokuapp.com/"
    private val LIKES = "likes/"
    private val COMMENTS = "comments/"

    override suspend fun getTopHeadlines(): Response<HeadlineResponse> {
        return api.getTopHeadlines()
    }

    override suspend fun getNumberOfLikes(url: String): Response<Likes> {
        return api.getNumberOfLikes(HEROKU_BASE_URL.plus(LIKES).plus(url))
    }

    override suspend fun getNumberOfComments(url: String): Response<ArticleComments> {
        return api.getNumberOfComments(HEROKU_BASE_URL.plus(COMMENTS).plus(url))
    }

}