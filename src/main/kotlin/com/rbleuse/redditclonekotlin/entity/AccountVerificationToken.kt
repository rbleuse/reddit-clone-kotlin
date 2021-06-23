package com.rbleuse.redditclonekotlin.entity

import org.hibernate.Hibernate
import java.time.Instant
import javax.persistence.*

@Table(name = "token")
@Entity
class AccountVerificationToken(
    val token: String,

    @OneToOne(fetch = FetchType.LAZY)
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var expirationDate: Instant? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AccountVerificationToken

        return id == other.id
    }

    override fun hashCode(): Int = 303911373
}
