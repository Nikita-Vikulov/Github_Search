package com.example.githubsearch.domain.interfaces

import com.example.githubsearch.domain.model.Repository

interface IRepositoryClickListener {
    fun onItemClick(currentRepository: Repository)
}
