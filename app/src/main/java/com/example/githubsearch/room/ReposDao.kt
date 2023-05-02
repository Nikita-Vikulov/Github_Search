package com.example.githubsearch.room

import androidx.room.*
import com.example.githubsearch.model.Repository

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(repos: Repository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        reposList: List<Repository>
    )

    @Delete
    suspend fun delete(repos: Repository)

    @Query("SELECT * FROM repos_table WHERE login = :login")
    suspend fun getReposByLogin(login: String): List<Repository>
}