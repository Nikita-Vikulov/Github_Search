package com.example.githubsearch.data.api

import com.example.githubsearch.domain.model.Repository
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
