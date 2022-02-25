package com.c653d0.passwordsaving

import android.app.Application
import androidx.lifecycle.*
import com.c653d0.passwordsaving.database.PasswordInfo
import com.c653d0.passwordsaving.database.PasswordRepository
import com.c653d0.passwordsaving.database.PasswordRoomDatabase
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PasswordViewModel(application: Application) : AndroidViewModel(application){

    private val pwDatabase:PasswordRoomDatabase = PasswordRoomDatabase.getDatabase(application.applicationContext)
    private val passwordRepository:PasswordRepository = PasswordRepository(pwDatabase.passWordDao())

    private val allPassword: LiveData<List<PasswordInfo>> = passwordRepository.allPassWord

    public fun getAllPassWord():LiveData<List<PasswordInfo>>{
        return allPassword
    }

    public fun insert(password:PasswordInfo) = viewModelScope.launch {
        passwordRepository.insert(password)
    }

    public fun delete(password:PasswordInfo) = viewModelScope.launch {
        passwordRepository.delete(password)
    }

    public fun update(password: PasswordInfo) = viewModelScope.launch {
        passwordRepository.update(password)
    }

    public fun deleteAll() = viewModelScope.launch {
        passwordRepository.deleteAll()
    }
}

class PasswordViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PasswordViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PasswordViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}