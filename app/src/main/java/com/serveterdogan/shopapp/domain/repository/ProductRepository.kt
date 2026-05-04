package com.serveterdogan.shopapp.domain.repository

import com.serveterdogan.shopapp.domain.model.Product

interface ProductRepository {

    suspend fun getProducts() : Result<List<Product>>

    suspend fun getProductById(productId : Int) : Result<Product>

    suspend fun searchProducts(query : String) : Result<List<Product>>

}