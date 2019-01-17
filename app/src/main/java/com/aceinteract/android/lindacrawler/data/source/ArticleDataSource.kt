package com.aceinteract.android.lindacrawler.data.source

import com.aceinteract.android.lindacrawler.data.model.Article

interface ArticleDataSource {

    interface DataSourceCallback {

        fun onDataNotAvailable()

    }

    interface GetArticlesCallback : DataSourceCallback {

        fun onArticlesLoaded(articles: List<Article>)

    }

    fun getArticle(callback: GetArticlesCallback)


}