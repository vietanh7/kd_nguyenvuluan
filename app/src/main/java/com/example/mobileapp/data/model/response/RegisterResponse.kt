package com.example.mobileapp.data.model.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("email")
    var email: MutableList<String> = mutableListOf(),

    @SerializedName("password")
    var password: MutableList<String> = mutableListOf()
)