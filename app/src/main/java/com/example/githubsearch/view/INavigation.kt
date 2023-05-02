package com.example.githubsearch.view

import com.example.githubsearch.model.Repository
import com.example.githubsearch.model.Users

interface INavigation {
    fun openHistoryFragment()
    fun openRepositoryFragment(currentRepository: Repository)
    fun openDetailsFragmentFromHistory(users: Users)
    fun openDetailsFragmentFromUsers(users: Users)
}