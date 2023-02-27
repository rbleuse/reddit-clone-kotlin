package com.rbleuse.redditclonekotlin.security

import com.rbleuse.redditclonekotlin.exception.ActivationException
import io.jsonwebtoken.Jwts
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.security.KeyStore
import java.security.PrivateKey

@Service
class JWTProvider {
    private val keystore: KeyStore

    init {
        try {
            keystore = KeyStore.getInstance("JKS")
            val resourceStream = this.javaClass.getResourceAsStream("/keystore.jks")
            keystore.load(resourceStream, "test123".toCharArray());
        } catch (ex: Exception) {
            throw ActivationException("Exception occured while loading keystore")
        }
    }

    fun generateToken(authentication: Authentication): String {
        val principal = authentication.principal as User
        return Jwts.builder().setSubject(principal.username).signWith(getPrivateKey()).compact()
    }

    private fun getPrivateKey(): PrivateKey {
        return try {
            keystore.getKey("alias", "test123".toCharArray()) as PrivateKey
        } catch (e: Exception) {
            throw ActivationException("Exception occurred while retrieving public key")
        }
    }
}
