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
        final val TYPE_BORROW = "TYPE_BORROW"
        final val TYPE_LEND = "TYPE_LEND"
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