package com.c653d0.passwordsaving

import com.c653d0.passwordsaving.tool.EncryptInterface
import com.c653d0.passwordsaving.tool.RsaEncrypt
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val encrypt:EncryptInterface = RsaEncrypt()
        encrypt.generateKey()
        val encryptionKey = encrypt.getEncryptionKey()
        val decryptionKey = encrypt.getDecryptionKey()
        print("theEncryptKeyIs:$encryptionKey")
        print("theDecryptKeyIs:$decryptionKey")
        val text = "hello,world!  ===  你好，世界！"
        val encodeText = encrypt.encrypt(text, encryptionKey)
        val decodeText = encrypt.encrypt(encodeText, decryptionKey)
        print("beforeEncode:$text")
        print("Encode:$encodeText")
        print("Decode:$decodeText")
    }
}