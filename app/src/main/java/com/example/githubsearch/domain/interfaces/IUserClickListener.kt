package com.example.githubsearch.domain.interfaces

import com.example.githubsearch.domain.model.Users

interface IUserClickListener {
    fun onItemClick(user: Users)
}
