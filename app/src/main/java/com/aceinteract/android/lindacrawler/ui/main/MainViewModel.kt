package com.aceinteract.android.lindacrawler.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.support.annotation.VisibleForTesting
import com.aceinteract.android.lindacrawler.Injection
import com.aceinteract.android.lindacrawler.LindaCrawlerApplication
import com.aceinteract.android.lindacrawler.R
import com.aceinteract.android.lindacrawler.SingleLiveEvent
import com.aceinteract.android.lindacrawler.data.model.Article
import com.aceinteract.android.lindacrawler.data.repository.ArticleRepository
import com.aceinteract.android.lindacrawler.data.source.ArticleDataSource

class MainViewModel(context: Application, private val articleRepository: ArticleRepository): AndroidViewModel(context) {

    private val isDataLoadingError = ObservableBoolean(false)

    internal val openWebpage = SingleLiveEvent<String>()

    val items: ObservableArrayList<Article> = ObservableArrayList()
    val dataLoading = ObservableBoolean(false)
    val empty = ObservableBoolean(false)
    val snackbarMessage = SingleLiveEvent<Int>()

    fun start() {
        loadArticles(true)
    }

    fun loadArticles(showLoadingUI: Boolean = false) {
        if (showLoadingUI) {
            dataLoading.set(true)
        }

        articleRepository.getArticle(object : ArticleDataSource.GetArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                if (showLoadingUI) {
                    dataLoading.set(false)
                }

                isDataLoadingError.set(false)

                with(items) {
                    clear()
                    addAll(articles)
                    empty.set(isEmpty())
                }
            }

            override fun onDataNotAvailable() {
                if (showLoadingUI) {
                    dataLoading.set(false)
                }
                isDataLoadingError.set(true)
                showSnackbarMessage(R.string.loading_data_error)
            }
        })
    }

    fun showSnackbarMessage(message: Int) {
        snackbarMessage.value = message
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory private constructor(private val context: Application,
                                               private val articleRepository: ArticleRepository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            if (isAssignableFrom(MainViewModel::class.java)) MainViewModel(context, articleRepository)
            else throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T

    companion object {

        @Volatile
        var INSTANCE: MainViewModelFactory? = null

        fun getInstance(application: LindaCrawlerApplication) =
            INSTANCE ?: synchronized(MainViewModelFactory::class.java) {
                INSTANCE ?: MainViewModelFactory(
                    application,
                    Injection.provideArticleRepository(application.applicationContext)
                ).also { INSTANCE = it }
            }

        @VisibleForTesting
        fun destroyInstance() {
            MainViewModelFactory.INSTANCE = null
        }

    }

}