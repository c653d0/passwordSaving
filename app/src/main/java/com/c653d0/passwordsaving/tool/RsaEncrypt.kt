package com.c653d0.passwordsaving.tool

import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import kotlin.collections.HashMap

class RsaEncrypt : EncryptInterface{
    private val map = HashMap<String, String>()


    public override fun generateKey() {
        //创建一个KeyPairGenerator对象用来创建密钥对
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        //初始化，设置密钥长度
        keyPairGenerator.initialize(2048, SecureRandom())
        //生成密钥对
        val keyPair = keyPairGenerator.genKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private
        //保存密钥
        map["public"] = Base64.getEncoder().encodeToString(publicKey.encoded)
        map["private"] = Base64.getEncoder().encodeToString(privateKey.encoded)
    }

    //获取解密密钥
    public override fun getDecryptionKey(): String {
        return map["private"]!!
    }
    //获取加密密钥
    public override fun getEncryptionKey(): String {
        return map["public"]!!
    }

    public override fun encrypt(text: String, EncryptionKey: String): String {
        val decode = Base64.getDecoder().decode(EncryptionKey)
        val pubKey: RSAPublicKey =
            KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decode)) as RSAPublicKey

        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)

        return Base64.getEncoder()
            .encodeToString(cipher.doFinal(text.toByteArray(charset = Charsets.UTF_8)))
    }

    public override fun decrypt(text: String, DecryptionKey: String): String {
        val inputText = Base64.getDecoder().decode(text.toByteArray(charset = Charsets.UTF_8))

        val decoded = Base64.getDecoder().decode(DecryptionKey)
        val priKey: RSAPrivateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(decoded)) as RSAPrivateKey

        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, priKey)
        return String(cipher.doFinal(inputText))
    }
}