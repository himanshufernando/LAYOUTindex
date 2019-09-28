package com.project.layoutindex.modeldata

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("page")
    var userListPage: Int = 0,
    @SerializedName("per_page")
    var userListPrePage: Int = 0,
    @SerializedName("total")
    var userListTotal: Int = 0,
    @SerializedName("total_pages")
    var userListTotalPages: Int = 0,
    @SerializedName("data")
    var userist: ArrayList<UserList> = ArrayList<UserList>(),
    var respondStatus: Boolean = true,
    var networkError: NetworkError = NetworkError()) {}