package com.serveterdogan.shopapp.domain.model

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)

data class Product(
    val id: Int,
    val title: String,
    val description: String? = null,
    val price: Double,
    val category: String? = null,
    val thumbnail: String,
    val brand: String,
    val rating: Double,
    val discountPercentage: Double? = null,
    val images: List<String>,
    val reviews: List<Review>,
    val isFavorite: Boolean = false,
    val  lastUpdated: Long? = null
)
