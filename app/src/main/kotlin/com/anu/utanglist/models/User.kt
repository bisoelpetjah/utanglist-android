package com.anu.utanglist.models

import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord

/**
 * Created by irvan on 2/19/16.
 */
class User: SugarRecord() {

    @SerializedName("user_id")
    var facebookId: String? = null

    @SerializedName("full_name")
    var name: String? = null

    @SerializedName("avatar")
    var photoUrl: String? = null

    var isCurrentUser: Boolean = false
}