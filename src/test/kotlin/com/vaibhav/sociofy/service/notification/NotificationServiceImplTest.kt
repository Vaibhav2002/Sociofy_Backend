package com.vaibhav.sociofy.service.notification


import com.google.common.truth.Truth.assertThat
import com.vaibhav.sociofy.exceptions.NotificationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class NotificationServiceImplTest {

    companion object {
        const val USER_ID = 1L
        const val POST_ID = 1L
        const val NOTIFICATION_DOES_NOT_EXIST = "Notification does not exist"
    }

    @Autowired
    private lateinit var service: NotificationServiceImpl

    @Test
    fun checkIfNotificationExistsWhenItDoesNot() {
        assertThat(service.checkIfNotificationExists(1)).isFalse()
    }

    @Test
    fun checkIfNotificationExistsWhenItDoes() {
        val notification = service.insertNotification(USER_ID, POST_ID)
        assertThat(service.checkIfNotificationExists(notification.notificationId)).isTrue()
    }

    @Test
    fun insertNotification() {
        val notification = service.insertNotification(USER_ID, POST_ID)
        assertThat(service.checkIfNotificationExists(notification.notificationId)).isTrue()
    }

    @Test
    fun getAllNotificationsByUserIdWhenThereAreNone() {
        val notifications = service.getAllNotificationsByUserId(USER_ID)
        assertThat(notifications).isEmpty()
    }

    @Test
    fun getAllNotificationsByUserIdWhenThereAre() {
        service.insertNotification(USER_ID, POST_ID)
        service.insertNotification(USER_ID, 2)
        service.insertNotification(USER_ID, 3)
        val notifications = service.getAllNotificationsByUserId(USER_ID)
        assertThat(notifications).isNotEmpty()
    }

    @Test
    fun getNotificationByPostIdWhenItDoesExist() {
        service.insertNotification(USER_ID, POST_ID)
        assertDoesNotThrow {
            service.getNotificationByPostId(POST_ID)
        }

    }

    @Test
    fun getNotificationByPostIdWhenItDoesNotExist() {
        val exception = assertThrows<NotificationException> {
            service.getNotificationByPostId(POST_ID)
        }
        assertThat(exception.message).isEqualTo(NOTIFICATION_DOES_NOT_EXIST)

    }

    @Test
    fun getAllNotificationByUserIdsWhereThereAre() {
        service.insertNotification(USER_ID, POST_ID)
        service.insertNotification(2, 2)
        val notifications = service.getAllNotificationByUserIds(listOf(USER_ID, 2, 3))
        assertThat(notifications).isNotEmpty()
    }

    @Test
    fun getAllNotificationByUserIdsWhereThereNone() {
        val notifications = service.getAllNotificationByUserIds(listOf(USER_ID, 2, 3))
        assertThat(notifications).isEmpty()
    }

    @Test
    fun deleteNotificationByPostId() {
        service.deleteNotificationByPostId(POST_ID)
        val exception = assertThrows<NotificationException> { service.getNotificationByPostId(POST_ID) }
        assertThat(exception.message).isEqualTo(NOTIFICATION_DOES_NOT_EXIST)
    }

    @Test
    fun deleteAllNotificationsByUserId() {
        service.deleteAllNotificationsByUserId(USER_ID)
        assertThat(service.getAllNotificationsByUserId(USER_ID)).isEmpty()
    }
}