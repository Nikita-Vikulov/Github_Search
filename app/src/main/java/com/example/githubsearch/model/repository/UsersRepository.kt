package com.example.githubsearch.model.repository


import com.example.githubsearch.model.Users
import com.example.githubsearch.model.UsersResponse
import com.example.githubsearch.model.repository.api.RetrofitInstance
import com.example.githubsearch.model.repository.room.UsersDao
import com.example.githubsearch.view.utils.INetworkStatus
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UsersRepository(
    private val usersDao: UsersDao,
    private val networkStatus: INetworkStatus
) {

    val allUsers: Flow<List<Users>> = usersDao.getAllUsers()

    suspend fun insertUser(users: Users) {
        usersDao.insert(users)
    }

    suspend fun getUsersByLogin(queryUser: String): List<Users> {
        if (networkStatus.isNetworkAvailableNow()) {
            val response = RetrofitInstance.api.getSearchUsers(queryUser)
            return convertResponseToUsersList(response)
        } else {
            return usersDao.getUsersByLogin(queryUser)
        }
    }

    private fun convertResponseToUsersList(response: Response<UsersResponse>): List<Users> {
        return response.body()?.items ?: emptyList()
    }
}