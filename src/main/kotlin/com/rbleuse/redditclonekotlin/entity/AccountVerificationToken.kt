package com.rbleuse.redditclonekotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.proxy.HibernateProxy
import java.time.Instant

@Table(name = "token")
@Entity
data class AccountVerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val token: String,
    @OneToOne(fetch = FetchType.LAZY)
    val user: User,
    val expirationDate: Instant? = null,
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as AccountVerificationToken

        return id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String = this::class.simpleName + "(  id = $id   ,   token = $token   ,   expirationDate = $expirationDate )"
}
