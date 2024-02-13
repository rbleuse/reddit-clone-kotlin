package com.rbleuse.redditclonekotlin.security

import io.jsonwebtoken.Jwts
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.security.PrivateKey
import java.util.Date

const val EXPIRATION_DURATION = 864_000_000L // 10 days

@Service
class JWTProvider(
    private val privateKey: PrivateKey,
) {
    fun generateToken(authentication: Authentication): String {
        val principal = authentication.principal as User
        return Jwts
            .builder()
            .subject(principal.username)
            .expiration(Date() + EXPIRATION_DURATION)
            .signWith(privateKey)
            .compact()
    }

    private infix operator fun Date.plus(milliseconds: Long): Date {
        return Date(time + milliseconds)
    }
}
