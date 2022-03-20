package com.example.mobileapp.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("token")
    var token: String? = null,

    @SerializedName("error")
    var error: String? = null
)