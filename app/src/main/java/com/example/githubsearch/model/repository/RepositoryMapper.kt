package com.example.githubsearch.model.repository

import com.example.githubsearch.model.Repository

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
