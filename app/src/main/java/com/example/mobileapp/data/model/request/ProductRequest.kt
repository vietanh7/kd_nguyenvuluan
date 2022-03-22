package com.example.mobileapp.data.model.request

import com.example.mobileapp.utils.Constants
import com.google.gson.annotations.SerializedName

data class ProductRequest internal constructor(

    @SerializedName(Constants.sku)
    val sku: String,
    @SerializedName(Constants.productName)

    val productName: String,
    @SerializedName(Constants.qty)
    val qty: Int,

    @SerializedName(Constants.price)
    val price: Float,

    @SerializedName(Constants.unit)
    val unit: String,

    @SerializedName(Constants.status)
    val status: Int
)