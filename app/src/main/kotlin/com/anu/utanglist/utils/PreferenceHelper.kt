package com.anu.utanglist.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by irvan on 2/26/16.
 */
object  PreferenceHelper {

    private final val PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN"
    private final val PREF_ECASH_ID = "PREF_ECASH_ID"
    private final val PREF_ECASH_PIN = "PREF_ECASH_PIN"

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

    var ecashId: String?
        get() {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_ECASH_ID, null)
        }
        set(value) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(PREF_ECASH_ID, value)
                    .commit()
        }

    var ecashPin: String?
        get() {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_ECASH_PIN, null)
        }
        set(value) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(PREF_ECASH_PIN, value)
                    .commit()
        }
}