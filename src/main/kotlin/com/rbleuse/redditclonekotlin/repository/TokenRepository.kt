package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.AccountVerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<AccountVerificationToken, Long> {

    fun findByToken(token: String): AccountVerificationToken?
}
