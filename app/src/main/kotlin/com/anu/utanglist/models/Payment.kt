package com.anu.utanglist.models

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by irvan on 2/23/16.
 */
@Table(name = "Payments", id = "_id")
class Payment: Model() {

    @Expose
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    var id: String? = null

    @Expose
    @Column
    @SerializedName("amount")
    var amount: Long = 0

    @Expose
    @Column
    @SerializedName("status")
    var status: String? = null
}