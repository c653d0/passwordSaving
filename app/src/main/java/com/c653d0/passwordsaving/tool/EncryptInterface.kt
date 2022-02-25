package com.c653d0.passwordsaving.tool

import java.security.PublicKey

interface EncryptInterface {
    public fun generateKey()

    public fun getEncryptionKey():String

    public fun getDecryptionKey():String

    public fun encrypt(text:String,EncryptionKey:String):String

    public fun decrypt(text:String,DecryptionKey:String):String
}