package com.c653d0.passwordsaving.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class PasswordInfo(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "label")
    val label:String,
    @ColumnInfo(name = "uid")
    val uid:String,
    @ColumnInfo(name = "password")
    val password:String,
    @ColumnInfo(name = "description")
    val description:String
) {
}