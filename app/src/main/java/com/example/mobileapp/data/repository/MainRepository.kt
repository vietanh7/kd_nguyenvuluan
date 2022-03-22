package com.example.mobileapp.data.repository

import com.example.mobileapp.data.api.ApiHelper
import com.example.mobileapp.data.model.request.ProductRequest
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun register(email: String, password: String) =
        apiHelper.register(email, password)

    suspend fun login(email: String, password: String) =
        apiHelper.login(email, password)

    suspend fun getAllProducts(header: String?) =
        apiHelper.getAllProducts(header)

    suspend fun addProduct(header: String?, request: ProductRequest) =
        apiHelper.addProduct(header, request)

    suspend fun updateProduct(header: String?, request: ProductRequest) =
        apiHelper.updateProduct(header, request)

    suspend fun deleteProduct(header: String?, sku: String) =
        apiHelper.deleteProduct(header, sku)

    suspend fun searchProducts(header: String?, sku: String) =
        apiHelper.searchProducts(header, sku)
}