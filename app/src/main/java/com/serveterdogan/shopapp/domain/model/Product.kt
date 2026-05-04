package com.serveterdogan.shopapp.domain.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String,
    val brand: String,
    val rating: Double
)
