package com.example.githubsearch.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "repos_table")
@Parcelize
data class Repository(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @SerializedName("node_id")
    var nodeId: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("full_name")
    var fullName: String = "",

    @SerializedName("description")
    var description: String = "",

    @SerializedName("language")
    var language: String = "",

    @SerializedName("forks_count")
    var forksCount: Int = 0,

    var login: String = "",

) :  Parcelable