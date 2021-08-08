package com.example.testnewsapp.di

import android.content.Context
import com.example.testnewsapp.news.HeadlinesFragment
import com.example.testnewsapp.newsdetail.NewsDetailFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author Abhradeep Ghosh
 */
@Singleton
@Component(modules = [AppModule::class, SourceModule::class])
interface AppComponent{

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: HeadlinesFragment)
    fun inject(fragment: NewsDetailFragment)

}