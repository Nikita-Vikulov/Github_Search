package com.example.githubsearch.domain

interface INavigation {
    fun openHistoryFragment()
    fun openRepositoryFragment(currentRepository: Repository)
    fun openDetailsFragmentFromHistory(users: Users)
    fun openDetailsFragmentFromUsers(users: Users)
}
