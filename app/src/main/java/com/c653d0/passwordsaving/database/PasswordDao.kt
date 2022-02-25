package com.c653d0.passwordsaving.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PasswordDao {
    @Query("SELECT * FROM password_table ORDER BY ID DESC")
    public fun getAllData():LiveData<List<PasswordInfo>>

    @Insert
    public suspend fun addPassword(vararg password:PasswordInfo)

    @Update
    public suspend fun updatePassword(vararg password: PasswordInfo)

    @Delete
    public suspend fun deletePassword(passwordInfo: PasswordInfo)

    @Query("DELETE FROM password_table")
    public suspend fun deleteAllData()

}