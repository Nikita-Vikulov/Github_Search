package com.example.githubsearch.view.users


/*class UsersViewModel(private val repository: UsersRepository) : ViewModel() {
    val allUsers: LiveData<List<Users>> = repository.allUsers.asLiveData()
    private val _myUsers = MutableLiveData<List<Users>>()
    val myUsers: LiveData<List<Users>> = _myUsers

    fun getUsersByLogin(queryUser: String) {
        viewModelScope.launch {
            val users = repository.getUsersByLogin(queryUser)
            _myUsers.postValue(users)
        }
    }
}

class ViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
