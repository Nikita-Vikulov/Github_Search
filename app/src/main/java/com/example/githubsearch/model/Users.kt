package com.example.githubsearch.model

import java.io.Serializable

data class Users(
        val login: String,
        val id: Int,
 //       val node_id: String,
        val avatar_url: String,
 /*       val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
 */      val repos_url: String,
   /*     val received_events_url: String,
        val type: String,
        val score: Int,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val events_url: String,
        val site_admin: Boolean
*/) : Serializable

/*
fun getUserLocalStorage(): List<Users> {
        return listOf(
                Users("user", 1, "https://secure.gravatar.com/avatar/25c7c18223fb42a4c6ae1c8db6f50f9b?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", "https://api.github.com/users/mojombo/repos" )
        )
}*/
