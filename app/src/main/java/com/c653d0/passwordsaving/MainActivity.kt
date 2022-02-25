package com.c653d0.passwordsaving

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.c653d0.passwordsaving.database.PasswordInfo
import com.c653d0.passwordsaving.tool.RsaEncrypt

class MainActivity : AppCompatActivity() {
    private final val TAG = "MainActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel:PasswordViewModel by viewModels {
            PasswordViewModelFactory(application)
        }


    }


}