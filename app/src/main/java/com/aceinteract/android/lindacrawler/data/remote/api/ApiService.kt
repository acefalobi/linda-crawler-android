package com.aceinteract.android.lindacrawler.data.remote.api

import com.aceinteract.android.lindacrawler.data.model.Article
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    fun getArticles(): Call<List<Article>>

}