package com.vaibhav.sociofy.models.response

import com.vaibhav.sociofy.models.entities.Post

data class PostResponse(
    val postData: Post,
    val username:String,
    val user_profile_image:String,
    val likedByMe:Boolean,
    val likeCount:Long
)
