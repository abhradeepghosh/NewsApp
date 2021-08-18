package com.example.testnewsapp.newsdetail

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.example.testnewsapp.R
import com.example.testnewsapp.data.*
import com.example.testnewsapp.util.Event
import com.example.testnewsapp.util.Format
import com.example.testnewsapp.util.pluralize
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */
class NewsDetailViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _articleId = MutableLiveData<Int>()

    private val _article = Transformations.switchMap(_articleId) {
        repository.observeArticle(it).map { article ->
            viewModelScope.launch {
                repository.getComments(article)
                repository.getLikes(article)
            }
            computeResult(article)
        }
    }

    private val _comments = _article.switchMap {
        repository.observeArticleNumberOfComments(it?.id).map { comments ->
            computeComments(comments)
        }
    }

    private val _likes = _article.switchMap {
        repository.observeArticleNumberOfLikes(it?.id).map { likes ->
            computeLikes(likes)
        }
    }

    val article: LiveData<Article?> = _article

    val comments: LiveData<Comments> = _comments

    val likes: LiveData<Likes?> = _likes

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _back = MutableLiveData<Event<Boolean>>()
    val back: LiveData<Event<Boolean>> = _back

    fun getNumberOfComments(numberOfComments: Int) : String? {
        return "comment".pluralize(numberOfComments)
    }

    fun getNumberOfLikes(numberOfLikes: Int) : String? {
        return "like".pluralize(numberOfLikes)
    }

    fun start(articleId: Int?) {
        // If we're already loading or already loaded, return (might be a config change)
        if (articleId == _articleId.value) {
            return
        }
        _articleId.value = articleId!!
    }

    fun back(){
        _back.value = Event(true)
    }


    private fun computeResult(taskResult: Result<Article>): Article? {
        return if (taskResult is Result.Success) {
            taskResult.data
        } else {
            showSnackbarMessage(R.string.loading_article_error)
            null
        }
    }

    private fun computeLikes(taskResult: Result<Likes>): Likes? {

        var resultLikes = Likes()

        if (taskResult is Result.Success) {
            viewModelScope.launch {
                taskResult.data?.let {
                    resultLikes = taskResult.data
                }
            }
        } else {
            resultLikes = Likes()
            showSnackbarMessage(R.string.loading_article_error)
        }
        return resultLikes
    }

    private fun computeComments(taskResult: Result<Comments>): Comments {

        var resultComments = Comments()

        if (taskResult is Result.Success) {
                viewModelScope.launch {
                    taskResult.data?.let {
                        resultComments = taskResult.data
                    }
                }
        } else {
            resultComments = Comments()
            showSnackbarMessage(R.string.loading_headlines_error)
        }

        return resultComments
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.value = Event(message)
    }

    // todo redundant : companion function not working with databinding in xml : need to find out solution
    fun dateFormat(dateTime: String?): String{
        return Format.dateFormatFromDateTime(dateTime)
    }
}