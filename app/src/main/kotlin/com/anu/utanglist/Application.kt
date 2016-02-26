package com.anu.utanglist

import com.anu.utanglist.utils.PreferenceHelper
import com.anu.utanglist.utils.WebServiceHelper
import com.facebook.FacebookSdk

/**
 * Created by irvan on 2/16/16.
 */
class Application: android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        PreferenceHelper.context = this

        WebServiceHelper.init()

        FacebookSdk.sdkInitialize(this)

        val accessToken = PreferenceHelper.accessToken
        WebServiceHelper.accessToken = accessToken
    }
}