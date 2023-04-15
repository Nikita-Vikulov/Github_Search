package com.example.githubsearch.model.repository.api

import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): Response<UsersResponse>

    @GET("/users/{login}/repos")
    suspend fun getReposOnline(
        @Path("login") login: String,
    ): Response<List<Repository>>
}

