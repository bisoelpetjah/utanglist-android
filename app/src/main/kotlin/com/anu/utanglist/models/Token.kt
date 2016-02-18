package com.anu.utanglist.models

import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord

/**
 * Created by irvan on 2/16/16.
 */
class Token: SugarRecord() {

    @SerializedName("token")
    var accessToken: String? = null
}