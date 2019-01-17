package com.aceinteract.android.lindacrawler.ui.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceinteract.android.lindacrawler.LindaCrawlerApplication
import com.aceinteract.android.lindacrawler.databinding.FragmentMainBinding
import com.aceinteract.android.lindacrawler.util.setupSnackbar

class MainFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentMainBinding
    private lateinit var recyclerAdapter: ArticleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentMainBinding.inflate(inflater,container, false).apply {
            viewModel = obtainViewModel(MainViewModelFactory.getInstance(activity!!.application as LindaCrawlerApplication))
            listener = object : MainActionListener {
                override fun onRefreshClicked() {
                    viewDataBinding.viewModel!!.loadArticles()
                }
            }
        }
        viewDataBinding.viewModel?.start()
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewModel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }
        setupToolbar()
        setupAdapter()

        viewDataBinding.viewModel!!.apply {
            openWebpage.observe(this@MainFragment, Observer {
                if (it != null) {
                    val urlIntent = Intent(Intent.ACTION_VIEW)
                    urlIntent.data = Uri.parse(it)
                    startActivity(urlIntent)
                }
            })
        }
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            recyclerAdapter = ArticleAdapter(ArrayList(0), viewModel)
            viewDataBinding.rvArticles.adapter = recyclerAdapter
            viewDataBinding.rvArticles.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupToolbar() {
        (activity!! as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)
    }

    private inline fun <reified T : ViewModel> obtainViewModel(viewModelFactory: ViewModelProvider.NewInstanceFactory) =
        android.arch.lifecycle.ViewModelProviders.of(this, viewModelFactory).get(T::class.java)


}
