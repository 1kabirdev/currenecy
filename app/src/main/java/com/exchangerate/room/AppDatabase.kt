package com.exchangerate.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exchangerate.model.WishlistDao

@Database(entities = [WishlistDao::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}