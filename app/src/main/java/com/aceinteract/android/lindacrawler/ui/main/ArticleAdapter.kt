package com.aceinteract.android.lindacrawler.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceinteract.android.lindacrawler.data.model.Article
import com.aceinteract.android.lindacrawler.databinding.ItemArticleBinding

class ArticleAdapter(private var articles: List<Article>, private val mainViewModel: MainViewModel) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private lateinit var binding: ItemArticleBinding

    fun replaceData(articles: List<Article>) {
        this.articles= articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        binding = ItemArticleBinding.inflate(inflater, p0, false)

        return ArticleViewHolder(binding.root)
    }

    override fun getItemCount(): Int = articles.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: ArticleViewHolder, p1: Int) {
        val itemActionListener = object : ArticleItemActionListener {
            override fun onArticleClicked(article: Article) {
                mainViewModel.openWebpage.value = article.url
            }
        }

        with(binding) {
            article = articles[p1]
            listener = itemActionListener
            executePendingBindings()
        }
    }


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}