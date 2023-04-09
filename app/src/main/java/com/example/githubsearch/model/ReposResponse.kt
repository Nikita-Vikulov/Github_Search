package com.example.githubsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "repos_table")
data class ReposResponse(
    @Expose
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @Expose
    @SerializedName("node_id")
    var nodeId: String = "",

    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("full_name")
    var fullName: String = "",

    @Expose
    @SerializedName("private")
    var private: Boolean = false

) : Serializable