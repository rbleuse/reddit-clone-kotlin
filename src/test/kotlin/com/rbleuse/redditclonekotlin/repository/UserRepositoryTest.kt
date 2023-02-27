package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `When findByUsername then return User`() {
        val userInDB = User("username", "password", "test@test.fr")
        userRepository.save(userInDB)

        val user = userRepository.findByUsername("username")
        assertThat(user).isNotNull
        assertThat(user?.userId).isEqualTo(1L)
    }
}
