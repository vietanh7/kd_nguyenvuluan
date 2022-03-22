package com.example.mobileapp.data.api

import com.example.mobileapp.data.model.request.ProductRequest
import com.example.mobileapp.data.model.response.LoginResponse
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.data.model.response.RegisterResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun register(email: String, password: String): Response<RegisterResponse>

    suspend fun login(email: String, password: String): Response<LoginResponse>

    suspend fun getAllProducts(header: String?): Response<MutableList<ProductResponse>>

    suspend fun addProduct(header: String?, request: ProductRequest): Response<ProductResponse>

    suspend fun updateProduct(header: String?, request: ProductRequest): Response<ProductResponse>

    suspend fun deleteProduct(header: String?, sku: String): Response<ProductResponse>

    suspend fun searchProducts(header: String?, sku: String): Response<ProductResponse>
}