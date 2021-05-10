package com.vaibhav.sociofy.repository

import com.vaibhav.sociofy.models.entities.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationRepository : JpaRepository<Notification, Long>{

    fun findByPostId(postId:Long): Notification?

    fun findAllByUserId(userId:Long):List<Notification>

    @Query("SELECT n FROM notification_table n WHERE n.userId IN ?1")
    fun findAllByUserIds(userIds:List<Long>):List<Notification>

    fun deleteAllByUserId(userId: Long)

    fun deleteByPostId(postId: Long)

}