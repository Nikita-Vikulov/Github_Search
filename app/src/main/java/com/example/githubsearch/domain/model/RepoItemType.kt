package com.example.githubsearch.domain.model

import com.google.gson.annotations.SerializedName

enum class RepoItemType {
    @SerializedName("dir")
    DIRECTORY,

    @SerializedName("file")
    FILE
}
