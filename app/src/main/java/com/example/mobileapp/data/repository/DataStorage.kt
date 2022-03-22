package com.example.mobileapp.data.repository

import com.amn.easysharedpreferences.EasySharedPreference
import com.example.mobileapp.data.model.response.UserResponse
import com.example.mobileapp.utils.Constants

object DataStorage {

    private val mapData: MutableMap<String, Any?> = mutableMapOf()

    fun checkLogin(): Boolean {
        return (getToken() != null)
    }

    fun saveToken(token: String) {
        mapData[Constants.KEY_HEADER_TOKEN] = token
    }

    fun getToken(): String? = mapData[Constants.KEY_HEADER_TOKEN] as? String

}