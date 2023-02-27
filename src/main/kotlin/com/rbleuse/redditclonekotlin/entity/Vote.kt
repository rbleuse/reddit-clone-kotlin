package com.rbleuse.redditclonekotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.Hibernate

enum class VoteType(direction: Int) {
    UPVOTE(1), DOWNVOTE(-1)
}

@Entity
@Table(name = "vote")
class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val voteId: Long,

    val voteType: VoteType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Vote

        return voteId == other.voteId
    }

    override fun hashCode(): Int = 1634544760
}
