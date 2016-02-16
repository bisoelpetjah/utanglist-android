package com.anu.utanglist

import com.anu.utanglist.utils.WebServiceHelper

/**
 * Created by irvan on 2/16/16.
 */
class Application: android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        WebServiceHelper.init()
    }
}