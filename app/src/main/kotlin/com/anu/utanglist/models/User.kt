package com.anu.utanglist.models

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by irvan on 2/19/16.
 */
@Table(name = "Users", id = "_id")
class User: Model() {

    @Expose
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("user_id")
    var facebookId: String? = null

    @Expose
    @Column
    @SerializedName("full_name")
    var name: String? = null

    @Expose
    @Column
    @SerializedName("avatar")
    var photoUrl: String? = null

    @Column
    var isCurrentUser: Boolean = false
}