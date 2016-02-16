package com.anu.utanglist.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by irvan on 2/16/16.
 */
@RealmClass
open class Token: RealmObject() {

    @PrimaryKey
    @Expose
    @SerializedName("token")
    open var accessToken: String? = null
}