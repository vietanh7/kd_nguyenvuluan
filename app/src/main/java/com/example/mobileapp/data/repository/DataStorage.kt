package com.example.mobileapp.data.repository

import com.amn.easysharedpreferences.EasySharedPreference
import com.example.mobileapp.data.model.response.UserResponse
import com.example.mobileapp.utils.Constants

object DataStorage {

    fun saveToken(token: String) {
        EasySharedPreference.putString(Constants.KEY_HEADER_TOKEN, token)
    }

    fun saveUser(email: String, password: String) {
        EasySharedPreference.putString(Constants.email, email)
        EasySharedPreference.putString(Constants.password, password)
    }

    fun getToken(): String = EasySharedPreference.getString(Constants.KEY_HEADER_TOKEN, "")
    fun getUser(): UserResponse {
        return UserResponse(
            email = EasySharedPreference.getString(Constants.email, ""),
            password = EasySharedPreference.getString(Constants.password, "")
        )
    }
}