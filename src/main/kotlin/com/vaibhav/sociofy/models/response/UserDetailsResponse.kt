package com.vaibhav.sociofy.models.response

import com.vaibhav.sociofy.models.entities.User

data class UserDetailsResponse(
    val userId:Long,
    val username:String,
    val profile_image_url:String,
    val bio:String,
    val followers:List<Long>,
    val following:List<Long>,
    val posts:List<PostResponse>
)