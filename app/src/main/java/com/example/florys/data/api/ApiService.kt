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
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Body request: LoginRequest,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("products-catalog")
        fun getAllProductCatalog(
        @Header("Authorization") token: String
    ): Call<ProductCatalogResponse>

    @GET("products-item-producer/{id}")
    fun getProductItemProducer(
        @Path("id") type: String
    ): Call<ProductItemResponse>

    @GET("products-item-distributor/{id}")
    fun getProductItemDistributor(
        @Path("id") type: String
    ): Call<ProductItemResponse>

    @GET("products-item-seller/{id}")
    fun getProductItemSeller(
        @Path("id") type: String
    ): Call<ProductItemResponse>

    @POST("product-item")
    fun uploadItemByProducer(
        @Body request: AddProductItemByProducerRequest,
    ): Call<ProductItemResponse>

    @POST("product-item")
    fun uploadItemByDistributor(
        @Body request: AddProductItemByDistributorRequest,
    ): Call<ProductItemResponse>

    @POST("product-item")
    fun uploadItemBySeller(
        @Body request: AddProductItemBySellerRequest,
    ): Call<ProductItemResponse>

    @GET("product-item/{id}")
    fun getProductItem(
        @Path("id") type: String
    ): Call<OneProductItemResponse>
}