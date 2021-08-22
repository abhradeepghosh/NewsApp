package com.example.testnewsapp.api

import com.example.testnewsapp.data.source.remote.NewsApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * @author Abhradeep Ghosh
 */

@RunWith(JUnit4::class)
class NewsApiTest : BaseServiceTest() {

    private lateinit var service: NewsApi

    @Before
    @Throws(IOException::class)
    fun createService() {
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Test
    fun getNewsSource() = runBlocking {
        enqueueResponse("news_source.json")
        val response = service.getTopHeadlines().body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check news source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.totalResults, CoreMatchers.`is`(87))
        MatcherAssert.assertThat(response.status, CoreMatchers.`is`("ok"))

        // Check list
        val articles = response.articles
        MatcherAssert.assertThat(articles, CoreMatchers.notNullValue())

        // Check item 1
        val article1 = articles[0]
        MatcherAssert.assertThat(article1, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(article1?.author, CoreMatchers.`is`("Abhradeep"))
        MatcherAssert.assertThat(article1?.title, CoreMatchers.`is`("Android"))
        MatcherAssert.assertThat(article1?.description, CoreMatchers.`is`("Latest in Android"))
        MatcherAssert.assertThat(article1?.source?.name, CoreMatchers.`is`("CNN"))
    }

    @Test
    fun getNumberOfCommentsSource() = runBlocking {
        enqueueResponse("comments_source.json")
        val response = service.getNumberOfComments("").body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check comments source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.comments, CoreMatchers.`is`(84))
    }

    @Test
    fun getNumberOfLikesSource() = runBlocking {
        enqueueResponse("likes_source.json")
        val response = service.getNumberOfLikes("").body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check comments source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.likes, CoreMatchers.`is`(92))
    }

}