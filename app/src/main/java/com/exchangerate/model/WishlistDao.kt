package com.exchangerate.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WishlistDao(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val currency: String,
    val price: Double
)