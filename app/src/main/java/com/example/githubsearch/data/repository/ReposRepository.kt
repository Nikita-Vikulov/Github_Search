package com.example.githubsearch.data.repository

import com.example.githubsearch.data.api.ApiService
import com.example.githubsearch.data.room.ReposDao
import com.example.githubsearch.domain.model.RepoContentItem
import com.example.githubsearch.domain.model.Repository

class ReposRepository(
    private val reposDao: ReposDao,
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
        return try {
            val responseRepos = apiService.getReposOnline(login)
            if (responseRepos.isSuccessful) {
                val repositoryList = responseRepos.body().orEmpty()
                insertRepos(repositoryList, login)
                reposDao.getReposByLogin(login)
            } else {
                reposDao.getReposByLogin(login)
            }
        } catch (e: Exception) {
            reposDao.getReposByLogin(login)
        }
    }

    suspend fun getRepoContents(owner: String, repoName: String, path: String = ""): List<RepoContentItem> {
        return try {
            val response = if (path.isBlank()) {
                apiService.getRepoContentsRoot(owner, repoName)
            } else {
                apiService.getRepoContents(owner, repoName, path)
            }
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFileContent(downloadUrl: String): String {
        return try {
            if (downloadUrl.isBlank()) return ""
            val response = apiService.getFileContent(downloadUrl)
            response.body()?.string().orEmpty()
        } catch (e: Exception) {
            ""
        }
    }
}
