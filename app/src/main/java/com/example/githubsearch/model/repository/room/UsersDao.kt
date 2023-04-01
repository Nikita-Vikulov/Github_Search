package com.example.githubsearch.model.repository.room

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.githubsearch.model.Users


@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(users: Users)

    @Delete
    suspend fun delete(users: Users)

    @Query("SELECT * from users_table")
    fun getAllUsers():LiveData<List<Users>>
}