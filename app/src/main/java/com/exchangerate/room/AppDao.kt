package com.exchangerate.room


import androidx.room.*
import com.exchangerate.model.WishlistDao

@Dao
interface AppDao {

    @Query("SELECT * FROM WishlistDao ORDER BY id DESC")
    fun getAll(): List<WishlistDao>

    @Insert
    fun insert(wishlist: WishlistDao)

    @Delete
    fun delete(wishlist: WishlistDao)

    @Delete
    fun deleteAll(wishlist: List<WishlistDao>)
}