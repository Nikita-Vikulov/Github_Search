package com.example.githubsearch.model.repository

import com.example.githubsearch.model.ReposResponse
import com.example.githubsearch.model.repository.api.RetrofitInstance
import com.example.githubsearch.model.repository.room.ReposDao
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ReposRepository(private val reposDao: ReposDao) {

    val allRepos: Flow<List<ReposResponse>> = reposDao.getAllRepos()

    suspend fun insertRepos(repos: ReposResponse) {
        reposDao.insert(repos)
    }

    suspend fun getReposByLogin(login: String): Response<List<ReposResponse>> {
        return RetrofitInstance.api.getRepos(login)
    }
}