package com.aceinteract.android.lindacrawler.data.repository

import com.aceinteract.android.lindacrawler.data.model.Article
import com.aceinteract.android.lindacrawler.data.remote.source.ArticleRemoteDataSource
import com.aceinteract.android.lindacrawler.data.source.ArticleDataSource

class ArticleRepository(private val articleRemoteDataSource: ArticleRemoteDataSource) :ArticleDataSource {

    override fun getArticle(callback: ArticleDataSource.GetArticlesCallback) {
        articleRemoteDataSource.getArticle(object : ArticleDataSource.GetArticlesCallback {

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onArticlesLoaded(articles: List<Article>) {
                callback.onArticlesLoaded(articles)
            }

        })
    }

    companion object {

        private var INSTANCE: ArticleRepository? = null

        @JvmStatic
        fun getInstance(articleRemoteDataSource: ArticleRemoteDataSource) =
            INSTANCE
                ?: synchronized(ArticleRepository::class.java) {
                    INSTANCE
                        ?: ArticleRepository(articleRemoteDataSource).also { INSTANCE = it }
                }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }

    }

}