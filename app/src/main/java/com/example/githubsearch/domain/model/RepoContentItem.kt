package com.example.githubsearch.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoContentItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("type")
    val type: String, // "file" or "dir"
    @SerializedName("size")
    val size: Long? = null,
    @SerializedName("download_url")
    val downloadUrl: String? = null
) : Parcelable
