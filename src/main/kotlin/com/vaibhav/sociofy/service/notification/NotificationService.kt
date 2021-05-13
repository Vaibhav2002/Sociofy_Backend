package com.vaibhav.sociofy.service.notification

import com.vaibhav.sociofy.models.entities.Notification

interface NotificationService {

    //insert
    fun insertNotification(userId: Long, postId: Long): Notification

    //fetch
    fun getAllNotificationsByUserId(userId: Long): List<Notification>

    fun getNotificationByPostId(postId: Long): Notification

    fun getAllNotificationByUserIds(userIds:List<Long>): List<Notification>




    //delete
    fun deleteNotificationByPostId(postId: Long)

    fun deleteAllNotificationsByUserId(userId: Long)

    fun deleteAllNotifications()
}