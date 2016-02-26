package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by irvan on 2/16/16.
 */
class Token {

    companion object {
        final val PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN"
    }

    @Expose
    @SerializedName("token")
    var accessToken: String? = null
}