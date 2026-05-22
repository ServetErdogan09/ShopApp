package com.serveterdogan.shopapp.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity (
    @PrimaryKey
    val id : Int,
    val title : String,
    val price : Double,
    val thumbnail : String,
    val rating: Double,
    val category: String,
    val brand: String,
    val lastUpdated: Long

)