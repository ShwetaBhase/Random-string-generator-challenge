package com.example.assignment

import androidx.lifecycle.LiveData
import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface RandomStringDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(randomString: RandomString)

    @Query("SELECT * FROM random_strings ORDER BY id DESC")
    fun getAll(): Flow<List<RandomString>>

    @Delete
    suspend fun delete(randomString: RandomString)

    @Query("DELETE FROM random_strings")
    suspend fun deleteAll()
}

