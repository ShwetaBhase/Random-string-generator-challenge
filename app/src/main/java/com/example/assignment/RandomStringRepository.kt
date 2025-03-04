package com.example.assignment

import android.util.Log
import androidx.lifecycle.LiveData

import kotlinx.coroutines.flow.Flow

class RandomStringRepository(private val dao: RandomStringDao) {
    val allStrings: Flow<List<RandomString>> = dao.getAll()

    suspend fun insert(randomString: RandomString) {
        dao.insert(randomString)
    }

    suspend fun delete(randomString: RandomString) {
        dao.delete(randomString)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}

