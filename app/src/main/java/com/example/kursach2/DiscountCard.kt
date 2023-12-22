package com.example.kursach2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discount_cards")
data class DiscountCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardNumber: String,
    val cardHolderName: String,
    val expirationDate: String
)