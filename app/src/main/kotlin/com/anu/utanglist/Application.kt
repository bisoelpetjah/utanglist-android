package com.anu.utanglist

import com.anu.utanglist.models.Token
import com.anu.utanglist.utils.WebServiceHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException

/**
 * Created by irvan on 2/16/16.
 */
class Application: android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        WebServiceHelper.init()

        try {
            Realm.getInstance(this)
        } catch(e: RealmMigrationNeededException) {
            Realm.deleteRealm(RealmConfiguration.Builder(this).build())
        }

        val token: Token? = Realm.getInstance(this)
                .where(Token::class.java)
                .findFirst()

        WebServiceHelper.accessToken = token?.accessToken
    }
}