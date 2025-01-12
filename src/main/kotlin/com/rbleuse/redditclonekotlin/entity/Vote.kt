package com.rbleuse.redditclonekotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.proxy.HibernateProxy

enum class VoteType(
    direction: Int,
) {
    UPVOTE(1),
    DOWNVOTE(-1),
}

@Entity
@Table(name = "vote")
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val voteId: Long = 0,
    val voteType: VoteType,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    val post: Post,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
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
        other as Vote

        return voteId == other.voteId
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String = this::class.simpleName + "(  voteId = $voteId   ,   voteType = $voteType )"
}
