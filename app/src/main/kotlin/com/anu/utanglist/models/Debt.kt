package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import java.util.List

/**
 * Created by irvan on 2/20/16.
 */
class Debt {

    @Expose
    var id: String? = null

    @Expose
    @SerializedName("user")
    var user: User? = null

    @Expose
    @SerializedName("total_debt")
    var totalAmount: Long = 0

    @Expose
    @SerializedName("current_debt")
    var currentAmount: Long = 0

    @Expose
    @SerializedName("notes")
    var note: String? = null

    @Expose
    @SerializedName("payments")
    var paymentList: List<Payment> = ArrayList<Payment>() as List<Payment>

    var type: Type? = null

    enum class Type {
        BORROW, LEND
    }
}