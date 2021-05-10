package com.vaibhav.sociofy.models.response

import com.vaibhav.sociofy.models.entities.User

data class UserDetailsResponse(
    val userData: User,
    val followers:List<Long>,
    val following:List<Long>,
)