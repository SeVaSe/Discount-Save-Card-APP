package com.example.kursach2

import androidx.room.*

@Dao
interface DiscountCardDao {

    @Query("SELECT * FROM discount_cards")
    suspend fun getAllCards(): List<DiscountCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: DiscountCard)

    @Delete
    suspend fun deleteCard(card: DiscountCard)
}