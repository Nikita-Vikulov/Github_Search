package com.example.githubsearch.repository


import com.example.githubsearch.api.ApiService
import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.room.UsersDao
import com.example.githubsearch.view.utils.INetworkStatus
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UsersRepository(
    private val usersDao: UsersDao,
    private val networkStatus: INetworkStatus,
    private val apiService: ApiService
) {

    val allUsers: Flow<List<Users>> = usersDao.getAllUsers()

    suspend fun insertUser(users: Users) {
        usersDao.insert(users)
    }

    suspend fun getUsersByLogin(queryUser: String): List<Users> {
        val searchQuery = "%$queryUser%"
        if (networkStatus.isNetworkAvailableNow()) {
            try {
                val response = apiService.getSearchUsers(queryUser)
                return convertResponseToUsersList(response)
            } catch (e: Exception) {
                return usersDao.getUsersByLogin(searchQuery)
            }
        } else {
            return usersDao.getUsersByLogin(searchQuery)
        }
    }

    private fun convertResponseToUsersList(response: Response<UsersResponse>): List<Users> {
        val items = response.body()?.items
        return if (items != null && items.isNotEmpty()) {
            items
        } else {
            emptyList()
        }
    }
}