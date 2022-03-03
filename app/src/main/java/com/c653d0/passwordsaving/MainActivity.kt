package com.c653d0.passwordsaving

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.c653d0.passwordsaving.database.PasswordInfo
import com.c653d0.passwordsaving.tool.RsaEncrypt

class MainActivity : AppCompatActivity() {
    private final val TAG = "MainActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

    }


}