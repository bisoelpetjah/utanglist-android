package com.anu.utanglist

import com.anu.utanglist.models.Token
import com.anu.utanglist.utils.WebServiceHelper
import com.orm.SugarContext
import com.orm.SugarRecord

/**
 * Created by irvan on 2/16/16.
 */
class Application: android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        WebServiceHelper.init()

        SugarContext.init(this)

        val token: Token? = SugarRecord.last(Token::class.java)

        WebServiceHelper.accessToken = token?.accessToken
    }

    override fun onTerminate() {
        super.onTerminate()

        SugarContext.terminate()
    }
}