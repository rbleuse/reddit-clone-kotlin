package com.rbleuse.redditclonekotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.hibernate.proxy.HibernateProxy
import java.time.Instant

@Table(name = "subreddit")
@Entity
data class Subreddit(
    @Id
    @SequenceGenerator(name = "SUB_GEN", sequenceName = "SEQ_SUB", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUB_GEN")
    val id: Long = 0,
    @NotBlank(message = "Subreddit name is required")
    val name: String,
    @NotBlank(message = "Subreddit description is required")
    val description: String,
    @OneToMany(fetch = FetchType.LAZY)
    val posts: List<Post>? = null,
    val creationDate: Instant,
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as Subreddit

        return id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String =
        this::class.simpleName +
            "(  id = $id   ,   name = $name   ,   description = $description   ,   creationDate = $creationDate )"
}
