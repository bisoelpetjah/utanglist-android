package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by irvan on 2/19/16.
 */
class User {

    @Expose
    @SerializedName("user_id")
    var facebookId: String? = null

    @Expose
    @SerializedName("full_name")
    var name: String? = null

    @Expose
    @SerializedName("avatar")
    var photoUrl: String? = null
}