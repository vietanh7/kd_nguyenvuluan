package com.example.mobileapp.data.api

import com.example.mobileapp.data.model.response.LoginResponse
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.data.model.response.RegisterResponse
import com.example.mobileapp.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //Register User
    @FormUrlEncoded
    @POST(Constants.ENDPOINT_REGISTER)
    suspend fun register(@Field(Constants.email) email: String, @Field(Constants.password) password: String): Response<RegisterResponse>

    //Login
    @FormUrlEncoded
    @POST(Constants.ENDPOINT_LOGIN)
    suspend fun login(@Field(Constants.email) email: String, @Field(Constants.password) password: String): Response<LoginResponse>

    //Get list product
    @GET(Constants.ENDPOINT_LIST_PRODUCT)
    suspend fun getAllProducts(@Header(Constants.authorization) authorization: String?): Response<MutableList<ProductResponse>>

    //Add product
    @FormUrlEncoded
    @POST(Constants.ENDPOINT_ADD_PRODUCT)
    suspend fun addProduct(
        @Header(Constants.authorization) authorization: String?,
        @Field(Constants.sku) sku: String,
        @Field(Constants.productName) productName: String,
        @Field(Constants.qty) qty: Int,
        @Field(Constants.price) price: Float,
        @Field(Constants.unit) unit: String,
        @Field(Constants.status) status: Int
    ): Response<ProductResponse>

    //Update product
    @FormUrlEncoded
    @POST(Constants.ENDPOINT_UPDATE_PRODUCT)
    suspend fun updateProduct(
        @Header(Constants.authorization) authorization: String?,
        @Field(Constants.sku) sku: String,
        @Field(Constants.productName) productName: String,
        @Field(Constants.qty) qty: Int,
        @Field(Constants.price) price: Float,
        @Field(Constants.unit) unit: String,
        @Query(Constants.status) status: Int
    ): Response<ProductResponse>

    //Delete product
    @FormUrlEncoded
    @POST(Constants.ENDPOINT_DELETE_PRODUCT)
    suspend fun deleteProduct(
        @Header(Constants.authorization) authorization: String?,
        @Field(Constants.sku) sku: String,
    ): Response<ProductResponse>


    //Search product
    @FormUrlEncoded
    @POST(Constants.ENDPOINT_SEARCH_PRODUCT)
    suspend fun searchProducts(
        @Header(Constants.authorization) authorization: String?,
        @Field(Constants.sku) sku: String,
    ): Response<ProductResponse>
}