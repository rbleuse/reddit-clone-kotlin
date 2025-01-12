package com.rbleuse.redditclonekotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.hibernate.proxy.HibernateProxy
import java.time.Instant

@Table(name = "post")
@Entity
data class Post(
    @Id
    @SequenceGenerator(name = "POST_GEN", sequenceName = "SEQ_POST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_GEN")
    val postId: Long = 0,
    @NotBlank(message = "Post Title is required")
    val postTitle: String,
    val url: String?,
    @Lob
    val description: String?,
    val voteCount: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,
    val creationDate: Instant,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    val subreddit: Subreddit,
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as Post

        return postId == other.postId
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String =
        this::class.simpleName +
            "(  postId = $postId   ,   creationDate = $creationDate   ,   voteCount = $voteCount   ,   description = $description   ,   url = $url   ,   postTitle = $postTitle )"
}
