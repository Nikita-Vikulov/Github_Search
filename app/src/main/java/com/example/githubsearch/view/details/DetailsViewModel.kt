package com.example.githubsearch.view.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.REALIZATION
import com.example.githubsearch.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    fun insert(users: Users, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REALIZATION.insertUser(users) {
                onSuccess()
            }
        }

    fun delete(users: Users, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REALIZATION.deleteUser(users) {
                onSuccess()
            }
        }
}