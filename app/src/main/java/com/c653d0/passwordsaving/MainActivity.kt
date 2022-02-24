package com.c653d0.passwordsaving

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.c653d0.passwordsaving.tool.RsaEncrypt

class MainActivity : AppCompatActivity() {
    private final val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rsaEncrypt = RsaEncrypt()

        rsaEncrypt.generateKey()
        val publicKey = rsaEncrypt.getPublicKey()
        val privateKey = rsaEncrypt.getPrivateKey()

        val text = "hello,world! == 你好，世界！"
        
        val encode = rsaEncrypt.encrypt(text, publicKey)
        val decode = rsaEncrypt.decrypt(encode,privateKey)

        Log.d(TAG, "onCreate: encode:$encode \ndecode:$decode")
    }


}