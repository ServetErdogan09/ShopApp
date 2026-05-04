package com.serveterdogan.shopapp.data.remote.service

import com.serveterdogan.shopapp.data.remote.dto.ProductDto
import com.serveterdogan.shopapp.data.remote.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): ProductResponseDto

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductDto

    @GET("products/search")
    suspend fun getSearchProduct(
        @Query("q") query: String
    ): ProductResponseDto
}
