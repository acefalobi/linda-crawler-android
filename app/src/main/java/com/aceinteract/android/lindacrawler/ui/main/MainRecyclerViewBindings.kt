package com.aceinteract.android.lindacrawler.ui.main

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.aceinteract.android.lindacrawler.data.model.Article

object MainRecyclerViewBindings {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<Article>) {
        with(recyclerView.adapter as ArticleAdapter) {

            replaceData(items)

        }
    }

}