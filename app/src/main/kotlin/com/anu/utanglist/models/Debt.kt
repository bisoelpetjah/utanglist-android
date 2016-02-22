package com.anu.utanglist.models

import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord

/**
 * Created by irvan on 2/20/16.
 */
class Debt: SugarRecord() {

    @SerializedName("lender_id")
    var lenderId: String? = null

    @SerializedName("borrower_id")
    var borrowerId: String? = null

    @SerializedName("user")
    var user: User? = null

    @SerializedName("total_debt")
    var totalAmount: Long = 0

    @SerializedName("current_debt")
    var currentAmount: Long = 0

    @SerializedName("notes")
    var note: String? = null
}