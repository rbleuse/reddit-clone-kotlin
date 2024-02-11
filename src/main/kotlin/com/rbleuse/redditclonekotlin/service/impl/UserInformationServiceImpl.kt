package com.rbleuse.redditclonekotlin.service.impl

import com.rbleuse.redditclonekotlin.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Collections

@Service
class UserInformationServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {
    private fun fetchAuths(role: String): Collection<GrantedAuthority> {
        return Collections.singletonList(SimpleGrantedAuthority(role))
    }

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user =
            userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found with username: $username")

        return User(
            user.username,
            user.password,
            user.accountStatus,
            true,
            true,
            true,
            fetchAuths("USER"),
        )
    }
}
