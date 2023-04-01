package com.example.githubsearch.model.repository

import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.api.RetrofitInstance
import retrofit2.Response


class RetrofitRepository() {
    suspend fun getUsers(queryUser: String): Response<UsersResponse> {
        return RetrofitInstance.api.getSearchUsers(queryUser)
    }
}

