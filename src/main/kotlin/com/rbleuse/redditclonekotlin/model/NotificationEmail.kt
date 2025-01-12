package com.rbleuse.redditclonekotlin.model

data class NotificationEmail(
    val subject: String,
    val recipient: String,
    val body: String,
)
