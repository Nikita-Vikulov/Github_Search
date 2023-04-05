package com.example.githubsearch.model.repository.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubsearch.model.Users

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class UsersRoomDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersRoomDatabase? = null

        fun getDatabase(context: Context): UsersRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersRoomDatabase::class.java,
                    "db"
                ).build()
                INSTANCE = instance

                instance
            }
        }

       /*private var database: UsersRoomDatabase? = null

        fun getInstance(context: Context): UsersRoomDatabase {

            return if (database == null) {
                database = Room
                    .databaseBuilder(context, UsersRoomDatabase::class.java, "db")
                    .build()
                database as UsersRoomDatabase

            } else {
                database as UsersRoomDatabase
            }

        }*/
    }
}