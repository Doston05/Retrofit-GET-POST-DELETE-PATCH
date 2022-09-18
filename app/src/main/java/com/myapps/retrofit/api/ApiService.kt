package com.myapps.retrofit.api

import com.myapps.retrofit.model.UserRequest
import com.myapps.retrofit.model.UserResponse
import com.myapps.retrofit.model.UserUpdateRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    @Headers(
        "Accept:application/json",
        "Content_Type:application/json",
        "Authorization: Bearer apikey"
    )
    fun getAllUsers(): Call<List<UserResponse>>


    @GET("users/{id}")
    @Headers(
        "Accept:application/json",
        "Content_Type:application/json",
        "Authorization: Bearer apikey"
    )
    fun getUser(@Path ("id") id:Int):Call<UserResponse>



    @GET("users")
    @Headers(
        "Accept:application/json",
        "Content_Type:application/json",
        "Authorization: Bearer apikey"
    )
    fun searchUser(@Query("name") query:String):Call<List<UserResponse>>

    @POST("users")
    @Headers(
        "Accept:application/json",
        "Content_Type:application/json",
        "Authorization: Bearer apikey"
    )
    fun createUser(@Body userObj:UserRequest):Call<UserResponse>


    @PATCH("users/{id}")
    @Headers(
        "Accept:application/json",
        "Content_Type:application/json",
        "Authorization: Bearer apikey"
    )
    fun updateUser(@Path ("id") id:Int,@Body userObj:UserUpdateRequest):Call<UserResponse>


    @DELETE("users/{id}")
    @Headers(
        "Accept:application/json",
        "Content_Type:application/json",
        "Authorization: Bearer apikey"
    )
    fun deleteUser(@Path ("id") id:Int):Call<UserResponse>

}