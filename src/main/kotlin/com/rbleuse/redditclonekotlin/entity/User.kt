package com.rbleuse.redditclonekotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.Hibernate
import java.time.Instant

@Table(name = "users")
@Entity
data class User(
    @Id
    @SequenceGenerator(name = "USER_GEN", sequenceName = "SEQ_USER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GEN")
    val userId: Long = 0,
    @NotBlank(message = "Username is required")
    val username: String,
    @NotBlank(message = "Password is required")
    val password: String,
    @Email
    @NotBlank(message = "Email is required")
    val email: String,
    val accountStatus: Boolean = false,
    val creationDate: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return userId == other.userId
    }

    override fun hashCode(): Int = this::class.hashCode()
}
