package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by irvan on 2/16/16.
 */
class Token {

    @Expose
    @SerializedName("token")
    var accessToken: String? = null
}