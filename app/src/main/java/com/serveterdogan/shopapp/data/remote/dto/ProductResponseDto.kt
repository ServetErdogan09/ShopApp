package com.serveterdogan.shopapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val products: List<ProductDto>,
    val reviews: List<ReviewDto>? = emptyList(),
    val total: Int,
    val skip: Int,
    val limit: Int
)
