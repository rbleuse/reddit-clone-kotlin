package com.rbleuse.redditclonekotlin.entity

import org.hibernate.Hibernate
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Table(name = "comment")
@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @NotEmpty
    val text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    val post: Post,

    val creationDate: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    val user: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Comment

        return id == other.id
    }

    override fun hashCode(): Int = 860659860
}
