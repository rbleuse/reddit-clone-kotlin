package com.rbleuse.redditclonekotlin.service

import com.rbleuse.redditclonekotlin.config.EMAIL_ACTIVATION
import com.rbleuse.redditclonekotlin.dto.RegisterRequest
import com.rbleuse.redditclonekotlin.entity.AccountVerificationToken
import com.rbleuse.redditclonekotlin.entity.User
import com.rbleuse.redditclonekotlin.exception.ActivationException
import com.rbleuse.redditclonekotlin.model.NotificationEmail
import com.rbleuse.redditclonekotlin.repository.TokenRepository
import com.rbleuse.redditclonekotlin.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenRepository: TokenRepository,
    private val mailService: MailService,
    private val mailBuilder: MailBuilder
) {

    @Transactional
    fun register(registerRequest: RegisterRequest) {
        val user = User(registerRequest.username, encodePassword(registerRequest.password), registerRequest.email)

        userRepository.save(user)

        val message = mailBuilder.build(
            "Welcome to React-Spring-Reddit Clone. " +
                    "Please visit the link below to activate you account : $EMAIL_ACTIVATION/${generateToken(user)}"
        )

        mailService.sendEmail(NotificationEmail("Please Activate Your Account", user.email, message))
    }

    fun generateToken(user: User): String {
        val token = UUID.randomUUID().toString()
        val verificationToken = AccountVerificationToken(token, user)

        tokenRepository.save(verificationToken)

        return token
    }

    fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    fun verifyToken(token: String) {
        val verificationToken = tokenRepository.findByToken(token)
        verificationToken.orElseThrow {
            ActivationException(
                "Invalid Activation Token"
            )
        }

        enableAccount(verificationToken.get())
    }

    fun enableAccount(token: AccountVerificationToken) {
        val username = token.user.username
        val user = userRepository.findByUsername(username)
            .orElseThrow {
                ActivationException("User not found with username: =$username")
            }

        user.accountStatus = true

        userRepository.save(user)
    }
}
