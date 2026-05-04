package com.serveterdogan.shopapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String,
    val brand: String,
    val rating: Double,
    val discountPercentage: Double,

)
