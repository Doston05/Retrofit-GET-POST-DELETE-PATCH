package com.myapps.retrofit.model

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("status")
    var status: String
)
