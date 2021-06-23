package com.rbleuse.redditclonekotlin.entity

import org.hibernate.Hibernate
import org.springframework.lang.Nullable
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Table(name = "post")
@Entity
class Post(
    @Id
    @SequenceGenerator(name = "POST_GEN", sequenceName = "SEQ_POST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_GEN")
    val postId: Long,

    @NotBlank(message = "Post Title is required")
    val postTitle: String,

    @Nullable
    val url: String?,

    @Nullable
    @Lob
    val description: String,

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
