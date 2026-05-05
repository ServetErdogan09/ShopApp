package com.serveterdogan.shopapp.ui.home

import com.serveterdogan.shopapp.domain.model.Product

data class ProductDetailState(
    val selectedProduct : Product? = null,
    val isLoading : Boolean = false,
    val isError: String? = null
)
