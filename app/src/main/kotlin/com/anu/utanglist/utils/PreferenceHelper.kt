package com.anu.utanglist.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by irvan on 2/26/16.
 */
object  PreferenceHelper {

    private final val PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN"

    var context: Context? = null

    var accessToken: String?
        get() {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_ACCESS_TOKEN, null)
        }
        set(value) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(PREF_ACCESS_TOKEN, value)
                    .commit()
        }
}