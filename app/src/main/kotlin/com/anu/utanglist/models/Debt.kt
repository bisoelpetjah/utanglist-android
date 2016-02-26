package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import java.util.List

/**
 * Created by irvan on 2/20/16.
 */
class Debt {

    companion object {
        final val TYPE_DEMAND = "TYPE_DEMAND"
        final val TYPE_OFFER = "TYPE_OFFER"
    }

    @Expose
    var id: String? = null

    @Expose
    @SerializedName("user")
    var user: User? = null

    @Expose
    @SerializedName("*")
    var users: List<User> = ArrayList<User>() as List<User>

    @Expose
    @SerializedName("total_debt")
    var totalAmount: Long = 0

    @Expose
    @SerializedName("notes")
    var note: String? = null

    @Expose
    @SerializedName("payments")
    var paymentList: List<Payment> = ArrayList<Payment>() as List<Payment>
}