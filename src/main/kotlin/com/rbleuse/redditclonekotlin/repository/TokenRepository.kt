package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.AccountVerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TokenRepository : JpaRepository<AccountVerificationToken, Long> {

    fun findByToken(token: String): Optional<AccountVerificationToken>
}
