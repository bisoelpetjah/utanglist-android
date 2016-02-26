package com.anu.utanglist

import android.preference.PreferenceManager
import com.anu.utanglist.models.Token
import com.anu.utanglist.utils.WebServiceHelper
import com.facebook.FacebookSdk

/**
 * Created by irvan on 2/16/16.
 */
class Application: android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        WebServiceHelper.init()

        FacebookSdk.sdkInitialize(this)

        val accessToken = PreferenceManager.getDefaultSharedPreferences(this).getString(Token.PREF_ACCESS_TOKEN, null)
        WebServiceHelper.accessToken = accessToken
    }
}