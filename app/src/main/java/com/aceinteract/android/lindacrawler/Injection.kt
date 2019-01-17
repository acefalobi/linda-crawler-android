package com.aceinteract.android.lindacrawler

import android.content.Context
import com.aceinteract.android.lindacrawler.data.remote.source.ArticleRemoteDataSource
import com.aceinteract.android.lindacrawler.data.repository.ArticleRepository
import com.aceinteract.android.lindacrawler.util.AppExecutors

object Injection {

    fun provideArticleRepository(context: Context): ArticleRepository {
        return ArticleRepository.getInstance(ArticleRemoteDataSource(AppExecutors()))
    }
}
