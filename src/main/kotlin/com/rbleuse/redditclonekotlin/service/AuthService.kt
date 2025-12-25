package com.rbleuse.redditclonekotlin.service

import com.rbleuse.redditclonekotlin.EMAIL_ACTIVATION
import com.rbleuse.redditclonekotlin.dto.AuthResponse
import com.rbleuse.redditclonekotlin.dto.LoginRequest
import com.rbleuse.redditclonekotlin.dto.RegisterRequest
import com.rbleuse.redditclonekotlin.entity.AccountVerificationToken
import com.rbleuse.redditclonekotlin.entity.User
import com.rbleuse.redditclonekotlin.exception.ActivationException
import com.rbleuse.redditclonekotlin.model.NotificationEmail
import com.rbleuse.redditclonekotlin.repository.TokenRepository
import com.rbleuse.redditclonekotlin.repository.UserRepository
import com.rbleuse.redditclonekotlin.security.JWTProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenRepository: TokenRepository,
    private val mailService: MailService,
    private val mailBuilder: MailBuilder,
    private val authenticationManager: AuthenticationManager,
    private val jwtProvider: JWTProvider,
) {
    @Transactional
    fun register(registerRequest: RegisterRequest) {
        val user =
            User(username = registerRequest.username, password = encodePassword(registerRequest.password), email = registerRequest.email)

        userRepository.save(user)

        val message =
            mailBuilder.build(
                """
                Welcome to React-Spring-Reddit Clone. Please visit the link below to activate you account : $EMAIL_ACTIVATION/${generateToken(
                    user,
                )}
                """.trimIndent(),
            )

        mailService.sendEmail(NotificationEmail("Please Activate Your Account", user.email, message))
    }

    fun login(loginRequest: LoginRequest): AuthResponse {
        val authenticate =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.username,
                    loginRequest.password,
                ),
            )
        SecurityContextHolder.createEmptyContext().authentication = authenticate
        val authToken = jwtProvider.generateToken(authenticate)
        return AuthResponse(authToken, loginRequest.username)
    }

    fun generateToken(user: User): String {
        val token = UUID.randomUUID().toString()
        val verificationToken = AccountVerificationToken(token = token, user = user)

        tokenRepository.save(verificationToken)

        return token
    }

    private fun encodePassword(password: String): String = passwordEncoder.encode(password)!!

    fun verifyToken(token: String) {
        val verificationToken =
            tokenRepository.findByToken(token) ?: throw ActivationException(
                "Invalid Activation Token",
            )

        enableAccount(verificationToken)
    }

    fun enableAccount(token: AccountVerificationToken) {
        val username = token.user.username

        val user = userRepository.findByUsername(username) ?: throw ActivationException("User not found with username: =$username")

        userRepository.save(user.copy(accountStatus = true))
    }
}
