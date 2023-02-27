package com.rbleuse.redditclonekotlin.repository

import com.rbleuse.redditclonekotlin.entity.Subreddit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubredditRepository : JpaRepository<Subreddit, Long> {

    fun findByName(name: String): Subreddit?
}
