package com.serveterdogan.shopapp.data.mapper

import com.serveterdogan.shopapp.data.remote.dto.ProductDto
import com.serveterdogan.shopapp.data.remote.dto.ReviewDto
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.model.Review

fun ProductDto.toProduct(): Product {
    return Product(
        id = id ?: 0,
        title = title ?: "İsimsiz Ürün",
        description = description ?: "Açıklama bulunamadı",
        price = price ?: 0.0,
        category = category ?: "Genel",
        thumbnail = thumbnail ?: "",
        brand = brand ?: "Markasız",
        rating = rating ?: 0.0,
        discountPercentage = discountPercentage ?: 0.0,
        images = images ?: emptyList(),
        reviews = reviews?.map { it.toReview() } ?: emptyList()
    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        rating = rating ?: 0,
        comment = comment ?: "",
        date = date ?: "",
        reviewerName = reviewerName ?: "Anonim Kullanıcı",
        reviewerEmail = reviewerEmail ?: ""
    )
}