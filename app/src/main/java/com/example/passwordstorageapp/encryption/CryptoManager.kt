package com.example.passwordstorageapp.encryption

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.GCMParameterSpec
import java.security.SecureRandom
import java.nio.charset.StandardCharsets

// in progress

class CryptoManager(
    private val key : ByteArray
) {

    fun encrypt(plainText : String){

    }

    fun decrypt(encryptedData: EncryptedData) : String{
        return "test"
    }
}

data class EncryptedData(
    val iv : ByteArray,
    val cipherText : ByteArray
)