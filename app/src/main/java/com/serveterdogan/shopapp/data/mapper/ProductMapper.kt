package com.serveterdogan.shopapp.data.mapper

import com.serveterdogan.shopapp.data.remote.dto.ProductDto
import com.serveterdogan.shopapp.domain.model.Product

fun ProductDto.toProduct() : Product{
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        category = category,
        thumbnail = thumbnail,
        brand = brand,
        rating = rating,
        discountPercentage = discountPercentage,
    )
}