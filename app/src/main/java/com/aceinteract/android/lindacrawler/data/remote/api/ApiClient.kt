package com.aceinteract.android.lindacrawler.data.remote.api

import android.util.Log
import com.aceinteract.android.lindacrawler.LindaCrawlerApplication
import com.aceinteract.android.lindacrawler.data.model.Article
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val apiBaseUrl = "${LindaCrawlerApplication.baseUrl}api/v1/"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
        .baseUrl(apiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit: Retrofit

    private var apiService: ApiService

    init {
        retrofit = builder.client(httpClient.build()).build()
        apiService = retrofit.create(ApiService::class.java)
    }

    interface ApiCallback<T> {

        fun onFail(call: Call<T>?, t: Throwable?)

        fun onResponse(call: Call<T>?, response: Response<T>?)

    }

    fun getArticles(callback: ApiCallback<List<Article>>) {

        val requestCall = apiService.getArticles()
        requestCall.enqueue(object : Callback<List<Article>> {

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                Log.e(this@ApiClient::getArticles.name, t.message)
                callback.onFail(call, t)
            }

            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                if (response.errorBody() != null) {
                    Log.e(this@ApiClient::getArticles.name, response.errorBody()!!.string())
                }
                callback.onResponse(call, response)
            }

        })

    }

}