package com.example.githubsearch.presentation.history

import com.example.githubsearch.domain.model.Users

object HistoryContract {
    data class State(
        val users: List<Users> = emptyList()
    )
}
