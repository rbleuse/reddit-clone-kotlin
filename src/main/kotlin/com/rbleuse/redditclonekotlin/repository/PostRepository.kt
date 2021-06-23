package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.Post
import com.rbleuse.redditclonekotlin.entity.Subreddit
import com.rbleuse.redditclonekotlin.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {

    fun findBySubreddit(subreddit: Subreddit): List<Post>

    fun findByUser(user: User): List<Post>
}
