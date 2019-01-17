package com.aceinteract.android.lindacrawler.data.remote.source

import com.aceinteract.android.lindacrawler.data.model.Article
import com.aceinteract.android.lindacrawler.data.remote.api.ApiClient
import com.aceinteract.android.lindacrawler.data.source.ArticleDataSource
import com.aceinteract.android.lindacrawler.util.AppExecutors
import retrofit2.Call
import retrofit2.Response

class ArticleRemoteDataSource(val appExecutors: AppExecutors) : ArticleDataSource {

    private val apiClient = ApiClient()

    override fun getArticle(callback: ArticleDataSource.GetArticlesCallback) {
        appExecutors.networkIO.execute {

            apiClient.getArticles(object : ApiClient.ApiCallback<List<Article>> {

                override fun onFail(call: Call<List<Article>>?, t: Throwable?) {
                    appExecutors.mainThread.execute { callback.onDataNotAvailable() }
                }

                override fun onResponse(call: Call<List<Article>>?, response: Response<List<Article>>?) {
                    appExecutors.mainThread.execute {

                        when (response!!.code()) {

                            200 -> callback.onArticlesLoaded(response.body()!!)
                            else -> callback.onDataNotAvailable()

                        }

                    }
                }

            })

        }
    }

}