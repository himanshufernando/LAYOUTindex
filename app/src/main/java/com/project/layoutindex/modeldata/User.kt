package com.project.layoutindex.modeldata

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data")
    var user: UserList = UserList(),
    var respondStatus: Boolean = true,
    var networkError: NetworkError = NetworkError()) {}