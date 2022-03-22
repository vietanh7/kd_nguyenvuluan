package com.example.mobileapp.data.model.response

import com.google.gson.annotations.SerializedName


data class ProductResponse(

    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("sku")
    var sku: String? = null,

    @SerializedName("product_name")
    var productName: String? = null,

    @SerializedName("qty")
    var qty: Int = 0,

    @SerializedName("price")
    var price: Float = 0F,

    @SerializedName("unit")
    var unit: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("status")
    var status: Int = 0,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    var updated_at: String? = null
)