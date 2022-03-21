package com.example.mobileapp.utils

interface ILoadingView {

    fun onShowLoading()
    fun onHideLoading()
    fun onShowError(isError: Boolean, error: String)
}