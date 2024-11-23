package com.example.florys.data.api

import com.example.florys.data.request.AddProductItemByDistributorRequest
import com.example.florys.data.request.AddProductItemByProducerRequest
import com.example.florys.data.request.AddProductItemBySellerRequest
import com.example.florys.data.request.LoginRequest
import com.example.florys.data.request.RegisterRequest
import com.example.florys.data.responses.LoginResponse
import com.example.florys.data.responses.OneProductItemResponse
import com.example.florys.data.responses.ProductCatalogResponse
import com.example.florys.data.responses.ProductItemResponse
import com.example.florys.data.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

    @POST("login")
    fun loginUser(
        @Body request: LoginRequest,
    ): Call<LoginResponse>
}



