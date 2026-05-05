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
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String,
    val brand: String,
    val rating: Double,
    val discountPercentage: Double,
    val images: List<String>,
    val reviews: List<Review>
)
