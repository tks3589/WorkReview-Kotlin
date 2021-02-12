package com.example.workreview_kotlin

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("members/3")
    suspend fun getMembers(): String

    @GET("products/{name}")
    fun getProduct(
        @Path("name")
        name :String
    ): Deferred<String>
}