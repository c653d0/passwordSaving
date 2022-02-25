package com.c653d0.passwordsaving.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class PasswordRepository(private val passwordDao: PasswordDao) {
    val allPassWord:LiveData<List<PasswordInfo>> = passwordDao.getAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    public suspend fun insert(passwordInfo: PasswordInfo){
        passwordDao.addPassword(passwordInfo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    public suspend fun update(passwordInfo: PasswordInfo){
        passwordDao.updatePassword(passwordInfo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    public suspend fun delete(passwordInfo: PasswordInfo){
        passwordDao.deletePassword(passwordInfo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    public suspend fun deleteAll(){
        passwordDao.deleteAllData();
    }
}