package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.Post
import com.rbleuse.redditclonekotlin.entity.User
import com.rbleuse.redditclonekotlin.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : JpaRepository<Vote, Long> {

    fun findTopByPostAndPost_UserOrderByVoteIdDesc(post: Post, user: User): Vote?
}
