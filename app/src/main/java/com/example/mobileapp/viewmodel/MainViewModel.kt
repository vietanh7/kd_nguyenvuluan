package com.example.mobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.model.request.ProductRequest
import com.example.mobileapp.data.model.response.LoginResponse
import com.example.mobileapp.data.model.response.ProductResponse
import com.example.mobileapp.data.model.response.RegisterResponse
import com.example.mobileapp.data.repository.MainRepository
import com.example.mobileapp.utils.NetworkHelper
import com.example.mobileapp.utils.Resource
import com.example.mobileapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var registerResponse: SingleLiveEvent<Resource<RegisterResponse>> = SingleLiveEvent()
    var loginResponse: SingleLiveEvent<Resource<LoginResponse>> = SingleLiveEvent()
    var getAllProductsResponse: SingleLiveEvent<Resource<MutableList<ProductResponse>>> =
        SingleLiveEvent()
    var addProductResponse: SingleLiveEvent<Resource<ProductResponse>> = SingleLiveEvent()
    var updateProductResponse: SingleLiveEvent<Resource<ProductResponse>> = SingleLiveEvent()
    var deleteProductResponse: SingleLiveEvent<Resource<ProductResponse>> = SingleLiveEvent()
    var searchProductResponse: SingleLiveEvent<Resource<ProductResponse>> = SingleLiveEvent()

    fun register(email: String, password: String) = viewModelScope.launch {
        registerResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.register(email, password).let {
                if (it.isSuccessful) {
                    registerResponse.postValue(Resource.success(it.body()))
                } else registerResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else registerResponse.postValue(Resource.error("No internet connection", null))
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        loginResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.login(email, password).let {
                if (it.isSuccessful) {
                    loginResponse.postValue(Resource.success(it.body()))
                } else loginResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else loginResponse.postValue(Resource.error("No internet connection", null))
    }

    fun getAllProducts(header: String?) = viewModelScope.launch {
        getAllProductsResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.getAllProducts(header).let {
                if (it.isSuccessful) {
                    getAllProductsResponse.postValue(Resource.success(it.body()))
                } else getAllProductsResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else getAllProductsResponse.postValue(Resource.error("No internet connection", null))
    }

    fun addProduct(header: String?, request: ProductRequest) = viewModelScope.launch {
        addProductResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.addProduct(header, request).let {
                if (it.isSuccessful) {
                    addProductResponse.postValue(Resource.success(it.body()))
                } else addProductResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else addProductResponse.postValue(Resource.error("No internet connection", null))
    }

    fun updateProduct(header: String?, request: ProductRequest) = viewModelScope.launch {
        updateProductResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.updateProduct(header, request).let {
                if (it.isSuccessful) {
                    updateProductResponse.postValue(Resource.success(it.body()))
                } else updateProductResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else updateProductResponse.postValue(Resource.error("No internet connection", null))
    }

    fun deleteProduct(header: String?, sku: String) = viewModelScope.launch {
        deleteProductResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.deleteProduct(header, sku).let {
                if (it.isSuccessful) {
                    deleteProductResponse.postValue(Resource.success(it.body()))
                } else deleteProductResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else updateProductResponse.postValue(Resource.error("No internet connection", null))
    }

    fun searchProduct(header: String?, sku: String) = viewModelScope.launch {
        searchProductResponse.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            repository.searchProducts(header, sku).let {
                if (it.isSuccessful) {
                    searchProductResponse.postValue(Resource.success(it.body()))
                } else searchProductResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else searchProductResponse.postValue(Resource.error("No internet connection", null))
    }
}