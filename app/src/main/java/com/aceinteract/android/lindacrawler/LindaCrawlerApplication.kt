package com.aceinteract.android.lindacrawler

import android.support.multidex.MultiDexApplication

class LindaCrawlerApplication : MultiDexApplication() {

    companion object {

        // Change to appropriate network address
        const val baseUrl = "http://192.168.8.102:8000/"

    }

}