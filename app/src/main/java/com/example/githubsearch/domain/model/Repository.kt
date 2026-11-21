package com.example.githubsearch.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "repos_table")
@Parcelize
data class Repository(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("node_id")
    val nodeId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("full_name")
    val fullName: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("forks_count")
    val forksCount: Int = 0,

    val login: String = ""
) : Parcelable
