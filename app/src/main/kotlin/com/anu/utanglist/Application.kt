package com.anu.utanglist

import com.activeandroid.ActiveAndroid
import com.activeandroid.query.Select
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

        ActiveAndroid.initialize(this)

        FacebookSdk.sdkInitialize(this)

        val token: Token? = Select().from(Token::class.java).executeSingle()
        WebServiceHelper.accessToken = token?.accessToken
    }
}