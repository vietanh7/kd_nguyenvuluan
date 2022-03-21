package com.example.mobileapp.utils

class Constants {

    companion object {

        //API
        const val BASE_URL = "https://hoodwink.medkomtek.net/api/"
        const val ENDPOINT_REGISTER = "register"
        const val ENDPOINT_LOGIN = "auth/login"
        const val ENDPOINT_ADD_PRODUCT = "item/add"
        const val ENDPOINT_UPDATE_PRODUCT = "item/update"
        const val ENDPOINT_DELETE_PRODUCT = "item/delete"
        const val ENDPOINT_SEARCH_PRODUCT = "item/search"
        const val ENDPOINT_LIST_PRODUCT = "items"

        const val email = "email"
        const val password = "password"
        const val authorization = "Authorization"
        const val sku = "sku"
        const val productName = "product_name"
        const val qty = "qty"
        const val price = "price"
        const val unit = "unit"
        const val status = "status"
        const val header = "Bearer"
        const val KEY_HEADER_TOKEN = "KEY_HEADER_TOKEN"

        const val TYPE_CLICK_EDIT = 1
        const val TYPE_CLICK_DELETE = 2
    }
}