package com.myapps.retrofit.model

import com.google.gson.annotations.SerializedName

data class UserUpdateRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("status")
    var status: String
)
