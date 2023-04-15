package com.example.githubsearch.view

import com.example.githubsearch.model.Users

interface IUserClickListener {
    fun onItemClick(user: Users)
}