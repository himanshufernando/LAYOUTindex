package com.project.layoutindex.modeldata

import com.google.gson.annotations.SerializedName

data class UserList (
    @SerializedName("id")
    var userID: Int = 0,
    @SerializedName("email")
    var userEmail: String = "",
    @SerializedName("first_name")
    var userFirstName: String = "",
    @SerializedName("last_name")
    var userLastName: String = "",
    @SerializedName("avatar")
    var userAvatar: String = ""
    ) {}