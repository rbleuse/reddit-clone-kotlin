package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest
    @Autowired
    constructor(
        private val userRepository: UserRepository,
    ) {
        companion object {
            @Container
            @ServiceConnection
            val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16.1-alpine")
        }

        @Test
        fun `When findByUsername then return User`() {
            val userInDB = User(username = "username", password = "password", email = "test@test.fr")
            userRepository.save(userInDB)

            val user = userRepository.findByUsername("username")
            assertThat(user).isNotNull
            assertThat(user?.userId).isEqualTo(1L)
        }
    }
