package com.example.githubsearch.model.repository.room

import androidx.room.*
import com.example.githubsearch.model.Users
import kotlinx.coroutines.flow.Flow


@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(users: Users)

    @Delete
    suspend fun delete(users: Users)

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): Flow<List<Users>>

    @Query("SELECT * FROM users_table WHERE login LIKE :login")
    suspend fun getUsersByLogin(login: String): List<Users>
}