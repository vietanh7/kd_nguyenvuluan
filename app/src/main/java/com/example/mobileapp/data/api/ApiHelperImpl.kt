package com.example.mobileapp.data.api

import com.example.mobileapp.data.model.request.ProductRequest
import com.example.mobileapp.data.model.response.LoginResponse
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.data.model.response.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun register(email: String, password: String): Response<RegisterResponse> =
        apiService.register(email, password)

    override suspend fun login(email: String, password: String): Response<LoginResponse> =
        apiService.login(email, password)

    override suspend fun getAllProducts(header: String?): Response<MutableList<ProductResponse>> =
        apiService.getAllProducts(header)

    override suspend fun addProduct(header: String?, request: ProductRequest): Response<ProductResponse> =
        apiService.addProduct(header, sku = request.sku, productName = request.productName, qty = request.qty, price = request.price, unit = request.unit, status = request.status)

    override suspend fun updateProduct(header: String?, request: ProductRequest): Response<ProductResponse> =
        apiService.updateProduct(header, sku = request.sku, productName = request.productName, qty = request.qty, price = request.price, unit = request.unit, status = request.status)

    override suspend fun deleteProduct(header: String?, sku: String): Response<ProductResponse> =
        apiService.deleteProduct(header, sku)

    override suspend fun searchProducts(header: String?, sku: String): Response<ProductResponse> =
        apiService.searchProducts(header, sku)
}