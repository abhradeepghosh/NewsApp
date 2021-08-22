package com.example.testnewsapp.source

import com.example.testnewsapp.data.source.DefaultNewsRepository
import com.nhaarman.mockitokotlin2.*
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Result
import com.example.testnewsapp.data.source.local.NewsDao
import com.example.testnewsapp.data.source.remote.*
import com.example.testnewsapp.source.local.FakeNewsLocalDataSource
import com.example.testnewsapp.source.remote.FakeNewsRemoteDataSource
import com.example.testnewsapp.util.MockitoTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Response

/**
 * @author Abhradeep Ghosh
 */
@RunWith(JUnit4::class)
class DefaultNewsRepositoryTest: MockitoTest() {

    private val articleRemote1 = Article(id=0, source = "CNN1", title = "Remote1")
    private val articleRemote2 = Article(id=0, source = "CNN2", title = "Remote2")

    private val remoteArticles = listOf<NewsArticle>(
        NewsArticle(source = Source(name = "CNN1"),title = "Remote1" ),
        NewsArticle(source = Source(name = "CNN2"),title = "Remote2" )
    )

    private val headlineResponse = HeadlineResponse(articles = remoteArticles)

    private val article1 = Article(id=1, source = "CNN1", title = "Local1")
    private val article2 = Article(id=2, source = "CNN2", title = "Local2")

    private val localArticle = listOf(article1,article2)
    private val remoteArticle = listOf(articleRemote1,articleRemote2)

    private lateinit var fakeNewsLocalDataSource: FakeNewsLocalDataSource
    private lateinit var fakeNewsRemoteDataSource: FakeNewsRemoteDataSource

    private lateinit var newsRepositoryTest: DefaultNewsRepository

    @Mock
    lateinit var newsDao: NewsDao

    @Mock
    lateinit var newsApi: NewsApi


    @Before
    fun createRepository(){
        fakeNewsLocalDataSource = FakeNewsLocalDataSource(localArticle.toMutableList())
        fakeNewsRemoteDataSource = FakeNewsRemoteDataSource(headlineResponse)
        newsRepositoryTest = DefaultNewsRepository(fakeNewsLocalDataSource,
            fakeNewsRemoteDataSource,
            Dispatchers.Unconfined)
    }

    @Test
    fun `verify news articles fetched are same with local`() = runBlockingTest{
        // GIVEN WHEN
        val articleResult = newsRepositoryTest.getArticles(true) as Result.Success

        // THEN
        assertThat(remoteArticle, `is`(articleResult.data))
    }

    @Test
    fun `get news headlines when there is internet`() = runBlocking {
        // GIVEN
        val response = Response.success(headlineResponse)

        // WHEN
        whenever(newsApi.getTopHeadlines()) doReturn response
        whenever(newsDao.getArticles()) doReturnConsecutively listOf(localArticle, remoteArticle)

        // THEN
        assertThat((newsRepositoryTest.getArticles(true) as Result.Success).data, `is`(remoteArticle))
    }

    @Test
    fun `get news headlines when there is no internet`() = runBlocking {
        // GIVEN
        val response = Response.success(headlineResponse)

        // WHEN
        whenever(newsApi.getTopHeadlines()) doReturn response
        whenever(newsDao.getArticles()) doReturn localArticle

        // THEN
        assertThat((newsRepositoryTest.getArticles(false) as Result.Success).data, `is`(localArticle))
    }

}