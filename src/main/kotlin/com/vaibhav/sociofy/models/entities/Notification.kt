package com.vaibhav.sociofy.models.entities

import javax.persistence.*

@Entity(name =  "notification_table")
@Table(name =  "notification_table")
data class Notification(


    val userId:Long,
    val postId:Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val notificationId:Long = 0,

    val timeStamp:String = System.currentTimeMillis().toString()
)

