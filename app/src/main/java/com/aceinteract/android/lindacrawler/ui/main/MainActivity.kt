package com.aceinteract.android.lindacrawler.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aceinteract.android.lindacrawler.R
import com.aceinteract.android.lindacrawler.data.model.Article

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentById(R.id.frame_container) ?:
        MainFragment().let {
            supportFragmentManager.beginTransaction().replace(R.id.frame_container, it).commit()
        }
    }
}

interface MainActionListener {

    fun onRefreshClicked()

}

interface ArticleItemActionListener {

    fun onArticleClicked(article: Article)

}