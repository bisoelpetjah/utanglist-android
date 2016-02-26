package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by irvan on 2/23/16.
 */
class Payment {

    @Expose
    var id: String? = null

    @Expose
    @SerializedName("amount")
    var amount: Long = 0

    @Expose
    @SerializedName("status")
    var status: String? = null

    @Expose
    @SerializedName("updated_at")
    var date: Date? = null
}