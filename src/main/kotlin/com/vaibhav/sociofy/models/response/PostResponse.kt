package com.vaibhav.sociofy.models.response

data class PostResponse(
    val postId: Long = 0,
    val userId: Long = 0,
    val description: String = "",
    val imageUrl: String = "",
    val username: String,
    val user_profile_image: String,
    val likes: List<Long>,
    val likeCount: Long,
    val timeStamp: String = System.currentTimeMillis().toString(),
)
