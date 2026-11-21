package com.example.githubsearch.data

import com.example.githubsearch.data.repository.RepositoryMapper
import com.example.githubsearch.domain.model.Repository
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryMapperTest {

    private val mapper = RepositoryMapper()

    @Test
    fun mapRepos_attachesLoginToAllRepositories() {
        val repos = listOf(
            Repository(id = 1, name = "Repo 1", login = ""),
            Repository(id = 2, name = "Repo 2", login = "")
        )
        val login = "octocat"

        val result = mapper.mapRepos(repos, login)

        assertEquals(2, result.size)
        assertEquals("octocat", result[0].login)
        assertEquals("octocat", result[1].login)
        assertEquals("Repo 1", result[0].name)
        assertEquals("Repo 2", result[1].name)
    }
}
