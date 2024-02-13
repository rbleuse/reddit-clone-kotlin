package com.rbleuse.redditclonekotlin.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyStore
import java.security.PrivateKey

@Configuration
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    @Value("\${jwt.secret}") private val secret: String,
) {
    private val secretArray = secret.toCharArray()
    private val resourceStream = this.javaClass.getResourceAsStream("/keystore.jks")

    @Bean
    fun privateKey(): PrivateKey {
        val keystore = KeyStore.getInstance("JKS")
        keystore.load(resourceStream, secretArray)

        return keystore.getKey("alias", secretArray) as PrivateKey
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize("/api/auth/**", permitAll)
                authorize(anyRequest, authenticated)
            }
        }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authProvider(passwordEncoder: PasswordEncoder): DaoAuthenticationProvider {
        return DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder)
        }
    }

    @Bean
    fun authManager(
        http: HttpSecurity,
        authProvider: DaoAuthenticationProvider,
    ): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(authProvider)
            .build()
    }
}
