package com.example.githubsearch.repository

import com.example.githubsearch.api.ApiService
import com.example.githubsearch.model.Repository
import com.example.githubsearch.room.ReposDao
import com.example.githubsearch.view.utils.INetworkStatus


class ReposRepository(
    private val reposDao: ReposDao,
    private val networkStatus: INetworkStatus,
    private val apiService: ApiService,
    private val repositoryMapper: RepositoryMapper
) {

    private suspend fun insertRepos(
        repos: List<Repository>,
        login: String
    ) {
        val reposLogin = repositoryMapper.mapRepos(repos, login)
        reposDao.insertAll(reposLogin)
    }

    suspend fun getReposByLogin(login: String): List<Repository> {
        if (networkStatus.isNetworkAvailableNow()) {
            val responseRepos = apiService.getReposOnline(login)
            val repositoryList: List<Repository> = responseRepos.body()?.toList() ?: emptyList()
            insertRepos(repositoryList, login)
            return reposDao.getReposByLogin(login)
        } else {
            return reposDao.getReposByLogin(login)
        }
    }
}