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
import org.hibernate.Hibernate
import java.time.Instant

@Table(name = "post")
@Entity
class Post(
    @Id
    @SequenceGenerator(name = "POST_GEN", sequenceName = "SEQ_POST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_GEN")
    val postId: Long,

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
    val subreddit: Subreddit
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Post

        return postId == other.postId
    }

    override fun hashCode(): Int = 949848249
}
