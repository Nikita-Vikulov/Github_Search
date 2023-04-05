package com.example.githubsearch.model.repository.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubsearch.model.Users

@Database(entities = [Users::class], version = 1)
abstract class UsersRoomDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

    companion object {
        private var database: UsersRoomDatabase? = null

        fun getInstance(context: Context): UsersRoomDatabase {

            return if (database == null) {
                database = Room
                    .databaseBuilder(context, UsersRoomDatabase::class.java, "db")
                    .build()
                database as UsersRoomDatabase

            } else {
                database as UsersRoomDatabase
            }

        }
    }
}