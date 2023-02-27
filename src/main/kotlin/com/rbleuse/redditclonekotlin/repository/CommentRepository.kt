package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.Comment
import com.rbleuse.redditclonekotlin.entity.Post
import com.rbleuse.redditclonekotlin.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByPost(post: Post): List<Comment>

    fun findByUser(user: User): List<Comment>
}
