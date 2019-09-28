package com.project.layoutindex.services.api

import com.project.layoutindex.modeldata.User
import com.project.layoutindex.modeldata.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("users")
    fun getAllUsers(@Query("page") page: Int): Observable<Users>


    @GET("users/{id}")
    fun getUsersById(@Path("id") userid: Int): Observable<User>



}