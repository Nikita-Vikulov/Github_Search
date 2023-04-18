package com.example.githubsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearch.model.repository.ReposRepository
import com.example.githubsearch.model.repository.UsersRepository

class ViewModelFactory(
    private val userRepository: UsersRepository,
    private val repoRepository: ReposRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(userRepository, repoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}