package com.example.kursach2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiscountCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun discountCardDao(): DiscountCardDao
}