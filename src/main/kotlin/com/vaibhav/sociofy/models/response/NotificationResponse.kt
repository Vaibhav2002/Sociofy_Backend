package com.vaibhav.sociofy.models.response

import com.vaibhav.sociofy.models.entities.Notification

data class NotificationResponse(
    val userId:Long,
    val postId:Long,
    val notificationId:Long,
    val username:String,
    val postImageUrl:String,
    val timeStamp:String = System.currentTimeMillis().toString()
)