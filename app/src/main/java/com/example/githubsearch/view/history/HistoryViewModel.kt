package com.example.githubsearch.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearch.REALIZATION
import com.example.githubsearch.model.Users

class HistoryViewModel : ViewModel() {
    fun getAllUsers(): LiveData<List<Users>> {
        return REALIZATION.allUsers
    }
}