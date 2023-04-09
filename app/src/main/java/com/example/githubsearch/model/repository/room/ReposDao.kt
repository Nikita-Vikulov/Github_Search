package com.example.githubsearch.model.repository.room

import androidx.room.*
import com.example.githubsearch.model.ReposResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(repos: ReposResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reposList: List<ReposResponse>)

    @Delete
    suspend fun delete(repos: ReposResponse)

    @Query("SELECT * from repos_table")
    fun getAllRepos(): Flow<List<ReposResponse>>

    // @Query("SELECT * FROM RoomGithubRepository WHERE userId = :userId")
   // fun getReposByUserId(userId: String): List<RoomGithubRepository>
}