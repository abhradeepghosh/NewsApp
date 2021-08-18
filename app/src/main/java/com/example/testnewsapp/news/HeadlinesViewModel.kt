package com.example.testnewsapp.news

import androidx.lifecycle.*
import com.example.testnewsapp.R
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.NewsRepository
import com.example.testnewsapp.data.Result
import com.example.testnewsapp.util.Event
import com.example.testnewsapp.util.Format
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */
class HeadlinesViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val isDataLoadingError = MutableLiveData<Boolean>()

    private val _items: LiveData<List<Article>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                repository.refreshArticles()
                _dataLoading.value = false
            }
        }

        repository.observeArticles().switchMap {
            transformArticles(it)
        }

    }

    val items: LiveData<List<Article>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _noArticlesLabel = MutableLiveData<Int>()
    val noArticlesLabel: LiveData<Int> = _noArticlesLabel

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _openArticleEvent = MutableLiveData<Event<Int>>()
    val openArticleEvent: LiveData<Event<Int>> = _openArticleEvent

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    /**
     * Called by Data Binding.
     */
    fun openArticle(articleId: Int) {
        _openArticleEvent.value = Event(articleId)
    }

    private fun transformArticles(headlineResult: Result<List<Article>>) : LiveData<List<Article>>{
        val result = MutableLiveData<List<Article>>()

        if (headlineResult is Result.Success<*>) {
            isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = headlineResult.data as List<Article>?
            }
        } else {
            result.value = emptyList()
            showSnackbarMessage(R.string.loading_headlines_error)
            isDataLoadingError.value = true
        }

        return result
    }

    fun refresh() {
        _forceUpdate.value = true
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data
     */
    fun loadHeadlines(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    // todo redundant : companion function not working with databinding in xml : need to find out solution
    fun dateFormat(dateTime: String?): String{
        return Format.dateFormatFromDateTime(dateTime)
    }

    init {
        loadHeadlines(true)
    }

}