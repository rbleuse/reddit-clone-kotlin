package com.rbleuse.redditclonekotlin.entity

import org.hibernate.Hibernate
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Table(name = "users")
@Entity
class User(
    @NotBlank(message = "Username is required")
    val username: String,

    @NotBlank(message = "Password is required")
    val password: String,

    @Email
    @NotBlank(message = "Email is required")
    val email: String,
) {
    @Id
    @SequenceGenerator(name = "USER_GEN", sequenceName = "SEQ_USER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GEN")
    var userId: Long? = null

    val creationDate: Instant = Instant.now()

    var accountStatus: Boolean = false

    fun isNew(): Boolean = userId == null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return userId == other.userId
    }

    override fun hashCode(): Int = 562048007
}
