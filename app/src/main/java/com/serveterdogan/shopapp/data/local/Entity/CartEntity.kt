package com.serveterdogan.shopapp.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val quantity: Int = 1,
    val category: String = "",
    val brand: String = ""
)
