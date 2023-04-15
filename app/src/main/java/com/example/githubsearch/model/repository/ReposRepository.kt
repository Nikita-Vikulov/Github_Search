package com.example.githubsearch.model.repository

import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.repository.api.RetrofitInstance
import com.example.githubsearch.model.repository.room.ReposDao
import com.example.githubsearch.view.utils.INetworkStatus


class ReposRepository(
    private val reposDao: ReposDao,
    private val networkStatus: INetworkStatus
) {

    private suspend fun insertRepos(
        repos: List<Repository>,
        login: String
    ) { //basemaping создать класс мапер
        val reposLogin = repos.map { repo ->
            repo.copy(
                login = login,
                nodeId = repo.nodeId ?: "",
                name = repo.name ?: "",
                fullName = repo.fullName ?: "",
                description = repo.description ?: "",
                language = repo.language ?: ""
            )
        }
        reposDao.insertAll(reposLogin)
    }

    suspend fun getReposByLogin(login: String): List<Repository> {
        if (networkStatus.isNetworkAvailableNow()) {
            val responseRepos = RetrofitInstance.api.getReposOnline(login)
            val repositoryList: List<Repository> = responseRepos.body()?.toList() ?: emptyList()
            insertRepos(repositoryList, login)
            return reposDao.getReposByLogin(login)
        } else {
            return reposDao.getReposByLogin(login)
        }
    }
}