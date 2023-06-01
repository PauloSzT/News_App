package com.example.newsapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapplication.data.local.dto.FavoriteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<FavoriteItem>>

    @Query("SELECT itemId FROM favorite")
    fun getAllIds(): Flow<List<String>>

    @Query("DELETE FROM favorite")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FavoriteItem)

    @Delete
    suspend fun deleteItem(item: FavoriteItem)
}
