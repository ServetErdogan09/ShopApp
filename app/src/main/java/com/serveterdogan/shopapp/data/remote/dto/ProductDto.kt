package com.serveterdogan.shopapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val rating: Int? = null,
    val comment: String? = null,
    val date: String? = null,
    val reviewerName: String? = null,
    val reviewerEmail: String? = null
)

@Serializable
data class ProductDto(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val thumbnail: String? = null,
    val brand: String? = null,
    val rating: Double? = null,
    val discountPercentage: Double? = null,
    val images: List<String>? = null,
    val reviews: List<ReviewDto>? = null
)
