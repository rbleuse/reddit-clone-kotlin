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
import org.hibernate.Hibernate
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Subreddit

        return id == other.id
    }

    override fun hashCode(): Int = 368711113
}
