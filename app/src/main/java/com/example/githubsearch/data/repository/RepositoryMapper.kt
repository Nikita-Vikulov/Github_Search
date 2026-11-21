package com.example.githubsearch.data.repository

import com.example.githubsearch.domain.model.Repository

class RepositoryMapper {
    fun mapRepos(repos: List<Repository>, login: String): List<Repository> {
        return repos.map { repo -> repo.copy(login = login) }
    }
}
