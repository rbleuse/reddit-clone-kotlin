package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>
}
