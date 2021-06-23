package com.rbleuse.redditclonekotlin.entity

import org.hibernate.Hibernate
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Table(name = "subreddit")
@Entity
class Subreddit(
    @Id
    @SequenceGenerator(name = "SUB_GEN", sequenceName = "SEQ_SUB", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUB_GEN")
    val id: Long,

    @NotBlank(message = "Subreddit name is required")
    val name: String,

    @NotBlank(message = "Subreddit description is required")
    val description: String,

    @OneToMany(fetch = FetchType.LAZY)
    val posts: List<Post>? = null,

    val creationDate: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Subreddit

        return id == other.id
    }

    override fun hashCode(): Int = 368711113
}
