package com.example.githubsearch.model.repository.api

import com.example.githubsearch.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/users?q=tom")
    suspend fun getUsers(): Response<UsersResponse>
}