package com.vaibhav.sociofy.service.notification

import com.vaibhav.sociofy.exceptions.NotificationException
import com.vaibhav.sociofy.models.entities.Notification
import com.vaibhav.sociofy.repository.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl @Autowired constructor(private val notificationRepository: NotificationRepository) :
    NotificationService {

    companion object{
        const val NOTIFICATION_DOES_NOT_EXIST = "Notification does not exist"
    }

    fun checkIfNotificationExists(notificationId: Long) = notificationRepository.existsById(notificationId)

    override fun insertNotification(userId: Long, postId: Long): Notification {
        val notification = Notification(userId, postId)
        return notificationRepository.save(notification)
    }

    override fun getAllNotificationsByUserId(userId: Long) =
        notificationRepository.findAllByUserId(userId)

    override fun getNotificationByPostId(postId: Long): Notification {
        val notification = notificationRepository.findByPostId(postId)
        return notification ?: throw NotificationException(NOTIFICATION_DOES_NOT_EXIST)
    }

    override fun getAllNotificationByUserIds(userIds: List<Long>) =
        notificationRepository.findAllByUserIds(userIds)

    override fun deleteNotificationByPostId(postId: Long) {
        notificationRepository.deleteByPostId(postId)
    }

    override fun deleteAllNotificationsByUserId(userId: Long) {
        notificationRepository.deleteAllByUserId(userId)
    }

    override fun deleteAllNotifications() = notificationRepository.deleteAll()
}