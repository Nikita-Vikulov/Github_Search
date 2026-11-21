package com.example.githubsearch.domain.interfaces

import com.example.githubsearch.domain.model.Repository
import com.example.githubsearch.domain.model.Users

interface INavigation {
    fun openHistoryFragment()
    fun openRepositoryFragment(currentRepository: Repository)
    fun openDetailsFragmentFromHistory(users: Users)
    fun openDetailsFragmentFromUsers(users: Users)
}
