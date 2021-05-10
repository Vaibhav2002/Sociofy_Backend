package com.vaibhav.sociofy.models.response

import com.vaibhav.sociofy.models.entities.Notification

data class NotificationResponse(
    val notification:Notification,
    val username:String,
    val postImageUrl:String,
)