package com.anu.utanglist.models

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import java.util.List

/**
 * Created by irvan on 2/20/16.
 */
@Table(name = "Debts", id = "_id")
class Debt: Model() {

    @Expose
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    var id: String? = null

    @Expose
    @Column
    @SerializedName("user")
    var user: User? = null

    @Expose
    @Column
    @SerializedName("total_debt")
    var totalAmount: Long = 0

    @Expose
    @Column
    @SerializedName("current_debt")
    var currentAmount: Long = 0

    @Expose
    @Column
    @SerializedName("notes")
    var note: String? = null

    @Expose
    @Column
    @SerializedName("payments")
    var paymentList: List<Payment> = ArrayList<Payment>() as List<Payment>

    @Column
    var type: Type? = null

    enum class Type {
        BORROW, LEND
    }
}