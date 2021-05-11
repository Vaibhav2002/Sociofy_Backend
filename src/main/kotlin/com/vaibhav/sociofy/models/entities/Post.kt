package com.vaibhav.sociofy.models.entities

import javax.persistence.*

@Entity(name = "post_table")
data class Post(
    val userId: Long = 0,
    val description: String = "",
    val imageUrl: String = "",
    val timeStamp: String = System.currentTimeMillis().toString(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postId: Long = 0L,

)
