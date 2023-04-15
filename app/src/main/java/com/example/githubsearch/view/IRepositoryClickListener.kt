package com.example.githubsearch.view

import com.example.githubsearch.model.Repository

interface IRepositoryClickListener {
    fun onItemClick(currentRepository: Repository)
}