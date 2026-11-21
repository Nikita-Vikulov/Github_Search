package com.example.githubsearch.data.repository

import com.example.githubsearch.domain.Repository

class RepositoryMapper {
    fun mapRepos(repos: List<Repository>, login: String): List<Repository> {
        return repos.map { repo ->
            repo.copy(
                login = login,
                nodeId = repo.nodeId?: "",
                name = repo.name?: "",
                fullName = repo.fullName?: "",
                description = repo.description?: "",
                language = repo.language?: ""
            )
        }
    }
}
