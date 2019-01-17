package com.aceinteract.android.lindacrawler.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:imageUrl")
fun ImageView.loadImageFromUrl(imageUrl: String) {

    Glide.with(context).asBitmap()
        .load(imageUrl)
        .thumbnail(.4f)
        .apply(RequestOptions().placeholder(android.R.color.darker_gray))
        .into(this)

}