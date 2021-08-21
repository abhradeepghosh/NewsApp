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

class NewsDetailViewModel @Inject constructor(private val repository: NewsRepository) :
    ViewModel() {

    private val _articleId = MutableLiveData<Int>()

    /**
     * Observe the article for a particular article id from local data source
     * result is transformed into LiveData of article.
     * And fire and forget to fetch the number of comments and number of likes
     * for a particular article from remote data source
     *
     * @return LiveData of one article
     */
    private val _article = Transformations.switchMap(_articleId) {
        repository.observeArticle(it).map { article ->
            _isLikesVisible.value = false
            _isCommentsVisible.value = false
            viewModelScope.launch {
                repository.getLikes(article)
                repository.getComments(article)
            }
            computeResult(article)
        }
    }

    /**
     * Observe the number of comments for a particular article id from local data source
     * result is transformed into LiveData of number of comments.
     *
     * @return LiveData of number of comments
     */
    private val _comments = _article.switchMap {
        repository.observeArticleNumberOfComments(it?.id).map { comments ->
            computeComments(comments)
        }
    }

    /**
     * Observe the number of likes for a particular article id from local data source
     * result is transformed into LiveData of number of likes.
     *
     * @return LiveData of number of likes
     */
    private val _likes = _article.switchMap {
        repository.observeArticleNumberOfLikes(it?.id).map { likes ->
            computeLikes(likes)
        }
    }

    val article: LiveData<Article?> = _article

    val comments: LiveData<Comments> = _comments

    val likes: LiveData<Likes> = _likes

    private val _isLikesVisible = MutableLiveData<Boolean>()
    val isLikesVisible: LiveData<Boolean> = _isLikesVisible

    private val _isCommentsVisible = MutableLiveData<Boolean>()
    val isCommentsVisible: LiveData<Boolean> = _isCommentsVisible

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _back = MutableLiveData<Event<Boolean>>()
    val back: LiveData<Event<Boolean>> = _back

    /**
     * To pluralize the comment text based on number of comments
     */
    fun getNumberOfComments(numberOfComments: Int): String? {
        return "comment".pluralize(numberOfComments)
    }

    /**
     * To pluralize the like text based on number of likes
     */
    fun getNumberOfLikes(numberOfLikes: Int): String? {
        return "like".pluralize(numberOfLikes)
    }

    /**
     * Receives the signal from the view and decision is made about the data observation
     */
    fun start(articleId: Int?) {
        // If we're already loading or already loaded, return (might be a config change)
        if (articleId == _articleId.value) {
            return
        }
        _articleId.value = articleId!!
    }

    /**
     * To handle the back navigation
     */
    fun back() {
        _back.value = Event(true)
    }

    /**
     * Transform the result containing articles into an article
     */
    private fun computeResult(taskResult: Result<Article>): Article? {
        return if (taskResult is Result.Success) {
            taskResult.data
        } else {
            showSnackbarMessage(R.string.loading_article_error)
            null
        }
    }

    /**
     * Transform the result containing number of likes into an object containing number of likes
     */
    private fun computeLikes(taskResult: Result<Likes?>): Likes {

        var resultLikes = Likes()

        if (taskResult is Result.Success) {
            viewModelScope.launch {
                taskResult.data?.let {
                    resultLikes = it
                    resultLikes.likes.let { numberOfLikes ->
                        if (numberOfLikes > 0) {
                            _isLikesVisible.value = true
                        }
                    }
                }
            }
        } else {
            showSnackbarMessage(R.string.loading_article_error)
        }

        return resultLikes
    }

    /**
     * Transform the result containing number of comments into an object containing number of comments
     */
    private fun computeComments(taskResult: Result<Comments?>): Comments {

        var resultComments = Comments()

        if (taskResult is Result.Success) {
            viewModelScope.launch {
                taskResult.data?.let {
                    resultComments = it
                    resultComments.comments.let { numberOfComments ->
                        if (numberOfComments > 0) {
                            _isCommentsVisible.value = true
                        }
                    }
                }
            }
        } else {
            showSnackbarMessage(R.string.loading_headlines_error)
        }

        return resultComments
    }

    /**
     * Display snackbar message
     */
    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.value = Event(message)
    }

    // todo redundant : companion function not working with databinding in xml : need to find out solution
    fun dateFormat(dateTime: String?): String {
        return Format.dateFormatFromDateTime(dateTime)
    }
}