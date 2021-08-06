package com.example.testnewsapp

import android.app.Application
import com.example.testnewsapp.di.AppComponent
import com.example.testnewsapp.di.DaggerAppComponent
import timber.log.Timber

/**
 * @author Abhradeep Ghosh
 */
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}