package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.Post
import com.rbleuse.redditclonekotlin.entity.User
import com.rbleuse.redditclonekotlin.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VoteRepository : JpaRepository<Vote, Long> {

    fun findTopByPostAndPost_UserOrderByVoteIdDesc(post: Post, user: User): Optional<Vote>
}
