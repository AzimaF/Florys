package com.example.florys.data.api

import com.example.florys.data.responses.ProductCatalogResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TestApiService {

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String
    ): ProductCatalogResponse
}