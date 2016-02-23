package com.anu.utanglist.models

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by irvan on 2/16/16.
 */
@Table(name = "Tokens",id = "_id")
class Token constructor(): Model() {

    @Expose
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("token")
    var accessToken: String? = null
}