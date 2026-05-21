package com.serveterdogan.shopapp.domain.usecase

import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend  fun getProducts() : Result<List<Product>>{
       return productRepository.getProducts()
    }


    suspend fun getProductById(productId : Int) : Result<Product>{
        return productRepository.getProductById(productId)
    }


    suspend fun searchProducts(query : String) : Result<List<Product>>{
        return  productRepository.searchProducts(query)
    }



    suspend fun getProductsByCategory(category : String) : Result<List<Product>>{
        return productRepository.getProductsByCategory(category)
    }


}