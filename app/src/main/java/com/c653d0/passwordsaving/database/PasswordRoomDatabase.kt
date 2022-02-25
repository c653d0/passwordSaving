package com.c653d0.passwordsaving.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PasswordInfo::class], version = 1, exportSchema = false)
abstract class PasswordRoomDatabase : RoomDatabase() {

    abstract fun passWordDao(): PasswordDao

    companion object {
        @Volatile
        private var INSTANCE: PasswordRoomDatabase? = null

        fun getDatabase(context: Context): PasswordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PasswordRoomDatabase::class.java,
                    "password_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}