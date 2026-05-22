package com.serveterdogan.shopapp.ui.favorite

import com.serveterdogan.shopapp.domain.model.Product

data class FavoriteState(
    val favoriteList : List<Product> = emptyList(),
    val isLoading : Boolean = false,
    val isError : String? = null
)
