package com.serveterdogan.shopapp.ui.home

import com.serveterdogan.shopapp.domain.model.Product

data class ProductState(
    val products : List<Product> = emptyList(),
    val isLoading : Boolean = false,
    val selectedProduct : Product? = null, // detay sayfası için
    val isError: String? = null,
    val selectedCategory: String = "Tümü",
    val isFromCache : Boolean = false,
    val lastUpdate : Long? = null
)
