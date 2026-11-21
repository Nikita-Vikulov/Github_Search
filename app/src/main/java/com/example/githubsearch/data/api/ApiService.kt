package com.example.githubsearch.data.api

import com.example.githubsearch.domain.model.RepoContentItem
import com.example.githubsearch.domain.model.Repository
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("/search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): Response<UsersResponse>

    @GET("/users/{login}/repos")
    suspend fun getReposOnline(
        @Path("login") login: String,
    ): Response<List<Repository>>

    @GET("/repos/{owner}/{repo}/contents")
    suspend fun getRepoContentsRoot(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<RepoContentItem>>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path", encoded = true) path: String
    ): Response<List<RepoContentItem>>

    @GET
    suspend fun getFileContent(@Url fileUrl: String): Response<ResponseBody>
}
