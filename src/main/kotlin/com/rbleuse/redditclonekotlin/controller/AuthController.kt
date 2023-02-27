package com.rbleuse.redditclonekotlin.controller

import com.rbleuse.redditclonekotlin.dto.AuthResponse
import com.rbleuse.redditclonekotlin.dto.LoginRequest
import com.rbleuse.redditclonekotlin.dto.RegisterRequest
import com.rbleuse.redditclonekotlin.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        authService.register(registerRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/verify/{token}")
    fun verify(@PathVariable token: String): ResponseEntity<Any> {
        authService.verifyToken(token)

        return ResponseEntity.ok("Account Activated")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): AuthResponse {
        return authService.login(loginRequest)
    }
}
